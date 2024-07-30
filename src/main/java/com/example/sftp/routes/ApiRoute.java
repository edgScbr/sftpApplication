package com.example.sftp.routes;

import com.example.sftp.entities.User;
import com.example.sftp.entities.UsersList;
import com.example.sftp.entities.models.AgeRangeSummary;
import com.example.sftp.entities.models.CitySummary;
import com.example.sftp.entities.models.GenderSummary;
import com.example.sftp.entities.models.OperatingSystemSummary;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.springframework.stereotype.Component;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ApiRoute extends RouteBuilder {


    @Override
    public void configure() throws Exception {

        BindyCsvDataFormat bindy = new BindyCsvDataFormat(User.class);
        bindy.setLocale("us");

        from("timer:fetchData?period=10000")
                .to("rest:get:?host=https://dummyjson.com/users")
                .log("---${body}")
                .process(exchange -> {
                    String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
                    exchange.getIn().setHeader("CamelFileName", "data_" + date + ".json");
                })
                .to("file:data/output/json?fileExist=Append")
                .unmarshal(new JacksonDataFormat(UsersList.class))
                .process(exchange -> {
                    String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
                    exchange.getIn().setHeader("CamelFileName", "ETL_" + date + ".csv");
                    UsersList userList = (UsersList) exchange.getIn().getBody();
                    exchange.getMessage().setBody(userList.getUsers());
                })
                .setProperty("userList").body()
                .marshal(bindy)
                .to("file:data/output/csv")
                //.log("${exchangeProperty.userList}")
                .process(exchange -> {
                    GenderSummary genderSummary = new GenderSummary();
                    UsersList userList = (UsersList) exchange.getProperty("userList");

                    List<User> males = userList.getUsers()
                            .stream()
                            .filter(i -> i.getGender().equals("male")).toList();

                    List<User> females = userList.getUsers()
                            .stream()
                            .filter(i -> i.getGender().equals("female")).toList();

                    List<User> others = userList.getUsers()
                            .stream()
                            .filter(i -> i.getGender().equals("other")).toList();

                    List<AgeRangeSummary> peopleByAge = new ArrayList<>();

                    for(int i=0; i <= 100; i+=10){
                        AgeRangeSummary sum = new AgeRangeSummary();
                        final int index = i;
                        int low = i == 0 ? 0 : 1;
                        if (i < 90) {
                            sum.setMaleCount(Math.toIntExact(males.stream().filter(m ->
                                    m.getAge() >= index + low && m.getAge() <= index + 10).count()));
                            sum.setFemaleCount(Math.toIntExact(females.stream().filter(f ->
                                    f.getAge() >= index + low && f.getAge() <= index + 10).count()));
                            sum.setOtherCount(Math.toIntExact(others.stream().filter(o ->
                                    o.getAge() >= index + low && o.getAge() <= index + 10).count()));

                            sum.setRange((index+low)+"-"+index+10);
                        } else {
                            sum.setMaleCount(Math.toIntExact(males.stream().filter(m ->
                                    m.getAge() >= index - 9).count()));
                            sum.setFemaleCount(Math.toIntExact(females.stream().filter(f ->
                                    f.getAge() >= index - 9).count()));
                            sum.setOtherCount(Math.toIntExact(others.stream().filter(o ->
                                    o.getAge() >= index - 9).count()));

                            sum.setRange(index-9+"+");
                        }
                        peopleByAge.add(sum);
                    }

                    //all cities
                    Set<String> cities = userList.getUsers().stream()
                            .map(u -> u.getAddress().getCity()).collect(Collectors.toSet());

                     List<CitySummary> citySummaries = new ArrayList<CitySummary>();

                    cities.forEach(c -> {
                        CitySummary sum = new CitySummary();
                        sum.setMaleCount(males.stream().filter(u ->
                            u.getAddress().getCity().equals(c)).count());
                        sum.setFemaleCount(females.stream().filter(u ->
                            u.getAddress().getCity().equals(c)).count());
                        sum.setFemaleCount(others.stream().filter(u ->
                            u.getAddress().getCity().equals(c)).count());
                        citySummaries.add(sum);
                        });
                    // all operating systems
                    Set<String> operatingSystems =userList.getUsers().stream()
                            .map(u -> {
                                int start = u.getUserAgent().indexOf("(");
                                int end = u.getUserAgent().indexOf(";");
                                return u.getUserAgent().substring(start, end);
                            }).collect(Collectors.toSet());

                    List<OperatingSystemSummary> operatingSystemSummaries = new ArrayList<>();

                    operatingSystems.forEach(os -> {
                        OperatingSystemSummary sum = new OperatingSystemSummary();
                        sum.setTotal(operatingSystems.stream().filter(o ->
                                o.equals(os)).count());
                        sum.setOs(os);
                        operatingSystemSummaries.add(sum);
                    });

                });

    }
}
