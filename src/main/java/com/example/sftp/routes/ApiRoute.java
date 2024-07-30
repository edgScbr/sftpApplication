package com.example.sftp.routes;

import com.example.sftp.entities.User;
import com.example.sftp.entities.UsersList;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.springframework.stereotype.Component;


import java.text.SimpleDateFormat;
import java.util.Date;

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
                .log("${exchangeProperty.userList}");




    }
}
