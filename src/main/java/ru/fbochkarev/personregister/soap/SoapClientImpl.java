package ru.fbochkarev.personregister.soap;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.soap.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class SoapClientImpl implements SoapClient {
    private String soapUrl = "http://94.125.90.50:6336/FNSINNFIODRSvc?wsdl";
    private final String Code = "FNSR01001";
    private final String Name = "ФНС России";

    @Override
    public String queryINNFLFIODR(Date dateBirth, String name, String surname, String patronymic) throws SOAPException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dateBirthS = dateFormat.format(dateBirth);
        SOAPConnectionFactory soapFactory = null;
        SOAPConnection soapConnect = null;
        SOAPMessage soapRequest = null;
        SOAPMessage soapResponse = null;

        try {
            // Создание SOAP Connection
            soapFactory = SOAPConnectionFactory.newInstance();
            soapConnect = soapFactory.createConnection();

            // Создание SOAP Message для отправки
            soapRequest = createSOAPRequestId(dateBirthS, name, surname, patronymic);
            // Получение SOAP Message
            soapResponse = soapConnect.call(soapRequest, soapUrl);

            // Печать SOAP Response
            System.out.println("Response SOAP Message:");
            soapResponse.writeTo(System.out);
            System.out.println();

            soapConnect.close();
        } catch (Exception e) {
            System.err.println("\nError occurred while sending SOAP Request to Server!\n"
                    + "Make sure you have the correct endpoint URL and SOAPAction!\n");
            e.printStackTrace();
        }

        //Проверка на наличие "КодОбр"
        String codeTest = findSoapElement(soapResponse, "КодОбр");
        if (codeTest.equals("99")) {
            return "99 - Системная ошибка. Возникает при наличии внутренних ошибок в INNFLFIODR";
        }

        return findSoapElement(soapResponse, "ИдЗапросФ");
    }

    @Override
    public String getINNFLFIODR(String idRequest) throws SOAPException {
        SOAPConnectionFactory soapFactory = null;
        SOAPConnection soapConnect = null;
        SOAPMessage soapRequest = null;
        SOAPMessage soapResponse = null;
        try {
            // Создание SOAP Connection
            soapFactory = SOAPConnectionFactory.newInstance();
            soapConnect = soapFactory.createConnection();

            // Создание SOAP Message для отправки
            soapRequest = createSOAPRequestINN(idRequest);
            // Получение SOAP Message
            soapResponse = soapConnect.call(soapRequest, soapUrl);

            // Печать SOAP Response
            System.out.println("Response SOAP Message:");
            soapResponse.writeTo(System.out);
            System.out.println();

            soapConnect.close();
        } catch (Exception e) {
            System.err.println("\nError occurred while sending SOAP Request to Server!\n"
                    + "Make sure you have the correct endpoint URL and SOAPAction!\n");
            e.printStackTrace();
        }

        //Проверка на наличие "КодОбр"
        String codeTest = findSoapElement(soapResponse, "КодОбр");
        switch (codeTest) {
            case "52":
                return "52 - Ответ не готов. Возникает при условии, что ответ еще не готов. Нужно обратиться позднее";
            case "83":
                return "83 - Отсутствует запрос с указанным идентификатором запроса. Возникает в ситуации, когда указан некорректный (неизвестный) идентификатор запроса";
            case "01":
                return "01 - Запрашиваемые сведения не найдены. Возникает при условии, что сведения об ИНН ФЛ не найдены";
            case "99":
                return "99 - Системная ошибка. Возникает при наличии внутренних ошибок в INNFLFIODR";
        }

        return findSoapElement(soapResponse, "ИННФЛ");
    }

    @Override
    public String findSoapElement(SOAPMessage soapMessage, String key) throws SOAPException {

        SOAPBody soapBody = soapMessage.getSOAPBody();
        // find your node based on tag name
        NodeList nodes = soapBody.getElementsByTagName(key);

        // check if the node exists and get the value
        String someMsgContent = null;
        Node node = nodes.item(0);
        someMsgContent = node != null ? node.getTextContent() : "";

        return someMsgContent;
    }

    @Override
    public SOAPMessage createSOAPRequestId(String dateBirth, String name, String surname, String patronymic) throws SOAPException, IOException {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
        envelope.addNamespaceDeclaration("wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        soapBody.addAttribute(envelope.createQName("Id", "wsu"), "body");

        //ws:queryINNFLFIODR
        SOAPElement ws = soapBody.addChildElement("queryINNFLFIODR", "ws", "http://ws.unisoft/");

        // Message
        SOAPElement message = ws.addChildElement("Message", "smev", "http://smev.gosuslugi.ru/rev111111");
        //Sender
        SOAPElement sender = message.addChildElement("Sender", "smev");
        SOAPElement codeS = sender.addChildElement("Code", "smev");
        codeS.addTextNode(Code);
        SOAPElement nameS = sender.addChildElement("Name", "smev");
        nameS.addTextNode(Name);
        //Recipient
        SOAPElement recipient = message.addChildElement("Recipient", "smev");
        SOAPElement codeR = recipient.addChildElement("Code", "smev");
        codeR.addTextNode(Code);
        SOAPElement nameR = recipient.addChildElement("Name", "smev");
        nameR.addTextNode(Name);
        //Originator
        SOAPElement originator = message.addChildElement("Originator", "smev");
        SOAPElement codeO = originator.addChildElement("Code", "smev");
        codeO.addTextNode(Code);
        SOAPElement nameO = originator.addChildElement("Name", "smev");
        nameO.addTextNode(Name);
        //TypeCode
        SOAPElement typeCode = message.addChildElement("TypeCode", "smev");
        typeCode.addTextNode("GSRV");
        //Status
        SOAPElement status = message.addChildElement("Status", "smev");
        status.addTextNode("REQUEST");
        //Date
        SOAPElement date = message.addChildElement("Date", "smev");
        date.addTextNode("2012-05-21T15:00:00Z");
        //ExchangeType
        SOAPElement exchangeType = message.addChildElement("ExchangeType", "smev");
        exchangeType.addTextNode("2");
        //TestMsg
        SOAPElement testMsg = message.addChildElement("TestMsg", "smev");

        //MessageData
        SOAPElement messageData = ws.addChildElement("MessageData", "smev", "http://smev.gosuslugi.ru/rev111111");
        //AppData
        SOAPElement appData = messageData.addChildElement("AppData", "smev", "http://smev.gosuslugi.ru/rev111111");
        appData.addAttribute(envelope.createQName("Id", "wsu"), "fns-AppData");
        //Документ
        SOAPElement dokument = appData.addChildElement("Документ", "tns", "http://ws.unisoft/INNFIODR/Query/Rq");
        dokument.setAttribute("ВерсФорм", "4.01");
        //СвФЛ
        SOAPElement svFL = dokument.addChildElement("СвФЛ", "tns");
        svFL.setAttribute("ДатаРожд", dateBirth);
        svFL.setAttribute("Имя", name);
        svFL.setAttribute("Фамилия", surname);
        //Отчество
        SOAPElement soapPatronymic = svFL.addChildElement("Отчество", "tns");
        soapPatronymic.addTextNode(patronymic);


        soapMessage.saveChanges();

        /* Print the request message */
        System.out.println("Request SOAP Message:");
        soapMessage.writeTo(System.out);

        System.out.println("");
        System.out.println("------");

        return soapMessage;
    }

    @Override
    public SOAPMessage createSOAPRequestINN(String idRequest) throws SOAPException, IOException {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
        envelope.addNamespaceDeclaration("wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        soapBody.addAttribute(envelope.createQName("Id", "wsu"), "body");

        //ws:queryINNFLFIODR
        SOAPElement ws = soapBody.addChildElement("getINNFLFIODR", "ws", "http://ws.unisoft/");

        // Message
        SOAPElement message = ws.addChildElement("Message", "smev", "http://smev.gosuslugi.ru/rev111111");
        //Sender
        SOAPElement sender = message.addChildElement("Sender", "smev");
        SOAPElement codeS = sender.addChildElement("Code", "smev");
        codeS.addTextNode(Code);
        SOAPElement nameS = sender.addChildElement("Name", "smev");
        nameS.addTextNode(Name);
        //Recipient
        SOAPElement recipient = message.addChildElement("Recipient", "smev");
        SOAPElement codeR = recipient.addChildElement("Code", "smev");
        codeR.addTextNode(Code);
        SOAPElement nameR = recipient.addChildElement("Name", "smev");
        nameR.addTextNode(Name);
        //Originator
        SOAPElement originator = message.addChildElement("Originator", "smev");
        SOAPElement codeO = originator.addChildElement("Code", "smev");
        codeO.addTextNode(Code);
        SOAPElement nameO = originator.addChildElement("Name", "smev");
        nameO.addTextNode(Name);
        //TypeCode
        SOAPElement typeCode = message.addChildElement("TypeCode", "smev");
        typeCode.addTextNode("GSRV");
        //Status
        SOAPElement status = message.addChildElement("Status", "smev");
        status.addTextNode("PING");
        //Date
        SOAPElement date = message.addChildElement("Date", "smev");
        date.addTextNode("2012-05-21T15:00:00Z");
        //ExchangeType
        SOAPElement exchangeType = message.addChildElement("ExchangeType", "smev");
        exchangeType.addTextNode("2");
        //RequestIdRef
        SOAPElement requestIdRef = message.addChildElement("RequestIdRef", "smev");
        requestIdRef.addTextNode("30DB597A-081A-73C4-B919-ABD83EADC558");
        //OriginRequestIdRef
        SOAPElement originRequestIdRef = message.addChildElement("OriginRequestIdRef", "smev");
        originRequestIdRef.addTextNode("30DB597A-081A-73C4-B919-ABD83EADC557");
        //TestMsg
        SOAPElement testMsg = message.addChildElement("TestMsg", "smev");

        //MessageData
        SOAPElement messageData = ws.addChildElement("MessageData", "smev", "http://smev.gosuslugi.ru/rev111111");
        //AppData
        SOAPElement appData = messageData.addChildElement("AppData", "smev", "http://smev.gosuslugi.ru/rev111111");
        appData.addAttribute(envelope.createQName("Id", "wsu"), "fns-AppData");
        //Документ
        SOAPElement dokument = appData.addChildElement("Документ", "tns", "http://ws.unisoft/INNFIODR/Get/Rq");
        dokument.setAttribute("ВерсФорм", "4.01");
        dokument.setAttribute("ИдЗапросФ", idRequest);

        soapMessage.saveChanges();

        /* Print the request message */
        System.out.println("Request SOAP Message11:");
        soapMessage.writeTo(System.out);

        System.out.println("");
        System.out.println("------");

        return soapMessage;
    }

}
