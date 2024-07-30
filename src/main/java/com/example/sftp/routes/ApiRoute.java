package com.example.sftp.routes;

import com.example.sftp.entities.User;
import com.example.sftp.entities.UsersList;
import com.example.sftp.entities.models.AgeRangeSummary;
import com.example.sftp.entities.models.CitySummary;
import com.example.sftp.entities.models.GenderSummary;
import com.example.sftp.entities.models.OperatingSystemSummary;
import com.example.sftp.repositories.AgeRangeRepository;
import com.example.sftp.repositories.CityRepository;
import com.example.sftp.repositories.GenderRepository;
import com.example.sftp.repositories.OperatingSystemRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ApiRoute extends RouteBuilder {

    @Autowired
    private AgeRangeRepository ageRangeRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private GenderRepository genderRepository;
    @Autowired
    private OperatingSystemRepository operatingSystemRepository;

    @Override
    public void configure() throws Exception {

        BindyCsvDataFormat bindy = new BindyCsvDataFormat(User.class);
        bindy.setLocale("us");

        BindyCsvDataFormat bindyGender = new BindyCsvDataFormat(GenderSummary.class);
        bindyGender.setLocale("us");

        BindyCsvDataFormat bindyPeopleByAge = new BindyCsvDataFormat(AgeRangeSummary.class);
        bindyPeopleByAge.setLocale("us");

        BindyCsvDataFormat bindyCity = new BindyCsvDataFormat(CitySummary.class);
        bindyCity.setLocale("us");

        BindyCsvDataFormat bindyOs = new BindyCsvDataFormat(OperatingSystemSummary.class);
        bindyOs.setLocale("us");


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
                .log("${exchangeProperty.userList}")
                .process(exchange -> {
                    //calculate gender summary
                    List<User> userList = (List<User>) exchange.getProperty("userList");
                    List<GenderSummary> genderSummaries = new ArrayList<>();
                    List<User> males = userList
                            .stream()
                            .filter(i -> i.getGender().equals("male")).toList();
                    genderSummaries.add(new GenderSummary("male", males.size()));

                    List<User> females = userList
                            .stream()
                            .filter(i -> i.getGender().equals("female")).toList();
                    genderSummaries.add(new GenderSummary("female", females.size()));

                    List<User> others = userList
                            .stream()
                            .filter(i ->
                                    !i.getGender().equals("male") && !i.getGender().equals("female")
                            ).toList();
                    genderSummaries.add(new GenderSummary("other", others.size()));
                    genderSummaries.forEach(s -> genderRepository.save(s));

                    //calculate users age summary
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

                            sum.setRange((index+low)+"-"+(index+10));
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
                        ageRangeRepository.save(sum);
                    }

                    //calculate cities summary
                    Set<String> cities = userList.stream()
                            .map(u -> u.getAddress().getCity()).collect(Collectors.toSet());

                     List<CitySummary> citySummaries = new ArrayList<>();

                    cities.forEach(c -> {
                        CitySummary sum = new CitySummary();
                        sum.setMaleCount(males.stream().filter(u ->
                            u.getAddress().getCity().equals(c)).count());
                        sum.setFemaleCount(females.stream().filter(f ->
                            f.getAddress().getCity().equals(c)).count());
                        sum.setOtherCount(others.stream().filter(u ->
                            u.getAddress().getCity().equals(c)).count());
                        sum.setCity(c);
                        citySummaries.add(sum);
                        cityRepository.save(sum);
                        });


                    // calculate operating systems summary
                    Set<String> operatingSystems =userList.stream()
                            .map(u -> {
                                int start = u.getUserAgent().indexOf("(") + 1;
                                int end = u.getUserAgent().indexOf(";");
                                return u.getUserAgent().substring(start, end);
                            }).collect(Collectors.toSet());

                    List<OperatingSystemSummary> operatingSystemSummaries = new ArrayList<>();

                    operatingSystems.forEach(os -> {
                        OperatingSystemSummary sum = new OperatingSystemSummary();
                        sum.setTotal(userList.stream().filter(u -> u.getUserAgent().contains(os)).count());
                        sum.setOs(os);
                        operatingSystemSummaries.add(sum);
                        operatingSystemRepository.save(sum);
                    });
                    exchange.setProperty("osSummary", operatingSystemSummaries);
                    exchange.setProperty("citySummary", citySummaries);
                    exchange.setProperty("peopleByAge", peopleByAge);
                    exchange.setProperty("genderSummary", genderSummaries);
                })
                // Generate Summary csv
                .setBody(exchange -> exchange.getProperty("genderSummary"))
                .process(exchange -> {
                    String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
                    exchange.getIn().setHeader("CamelFileName", "summary_" + date + ".csv");

                })
                .log("${body}")
                .marshal(bindyGender)
                .to("file:data/output/csv?fileExist=Append")

                .setBody(exchange -> exchange.getProperty("peopleByAge"))
                .marshal(bindyPeopleByAge)
                .to("file:data/output/csv?fileExist=Append")

                .setBody(exchange -> exchange.getProperty("citySummary"))
                .marshal(bindyCity)
                .to("file:data/output/csv?fileExist=Append")

                .setBody(exchange -> exchange.getProperty("osSummary"))
                .marshal(bindyOs)
                .to("file:data/output/csv?fileExist=Append");;




    }
}
