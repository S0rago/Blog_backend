package main.service;

import main.Response.AbstractResponse;
import main.Response.InitResponse;
import org.springframework.stereotype.Service;

@Service
public class ApiGeneralService {
    public AbstractResponse getData() {
        InitResponse ri = new InitResponse();
        ri.setTitle("DevPub");
        ri.setSubtitle("Рассказы разработчиков");
        ri.setPhone("+7 903 666-44-55");
        ri.setEmail("mail@mail.ru");
        ri.setCopyright("Дмитрий Сергеев");
        ri.setCopyrightFrom("2005");

        return ri;
    }
}
