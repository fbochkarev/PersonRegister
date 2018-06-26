package ru.fbochkarev.personregister.soap;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

public interface SoapClient {

    //Получение ИД-запроса
    public String queryINNFLFIODR (Date dateBirth, String name, String surname, String patronymic) throws SOAPException;

    //Получение ИНН
    public String getINNFLFIODR(String idRequest) throws SOAPException;

    //Создание SOAPMessage для получение ИД-запроса
    public SOAPMessage createSOAPRequestId(String dateBirth, String name, String surname, String patronymic) throws SOAPException, IOException;

    //Создание SOAPMessage для получение ИНН
    public SOAPMessage createSOAPRequestINN(String idRequest) throws SOAPException, IOException;

    //Ищим значение нужного элемента в SOAPMessage
    public String findSoapElement(SOAPMessage soapMessage, String key) throws SOAPException;
}
