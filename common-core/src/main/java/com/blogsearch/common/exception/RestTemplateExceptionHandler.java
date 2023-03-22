//package com.blogsearch.common.exception;
//
//import org.springframework.http.client.ClientHttpResponse;
//import org.springframework.lang.Nullable;
//import org.springframework.stereotype.Component;
//import org.springframework.util.ObjectUtils;
//import org.springframework.web.client.DefaultResponseErrorHandler;
//
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.Reader;
//import java.nio.CharBuffer;
//import java.nio.charset.Charset;
//import java.nio.charset.StandardCharsets;
//
//@Component
//public class RestTemplateExceptionHandler extends DefaultResponseErrorHandler {
//
//    public String getMessage(
//            int rawStatusCode, String statusText, @Nullable byte[] responseBody, @Nullable Charset charset) {
//
//        String preface = rawStatusCode + " " + statusText + ": ";
//        if (ObjectUtils.isEmpty(responseBody)) {
//            return preface + "[no body]";
//        }
//
//        if (charset == null) {
//            charset = StandardCharsets.UTF_8;
//        }
//        int maxChars = 200;
//
//        if (responseBody.length < maxChars * 2) {
//            return preface + "[" + new String(responseBody, charset) + "]";
//        }
//
//        try {
//            Reader reader = new InputStreamReader(new ByteArrayInputStream(responseBody), charset);
//            CharBuffer buffer = CharBuffer.allocate(maxChars);
//            reader.read(buffer);
//            reader.close();
//            buffer.flip();
//            return preface + "[" + buffer.toString() + "... (" + responseBody.length + " bytes)]";
//        }
//        catch (IOException ex) {
//            // should never happen
//            throw new IllegalStateException(ex);
//        }
//    }
//
//    @Override
//    public boolean hasError(ClientHttpResponse response) throws IOException {
//        boolean hasError = false;
//        int rawStatusCode = response.getRawStatusCode();
//        if (rawStatusCode != 200) {
//            hasError = true;
//        }
//        return hasError;
//    }
//
//    @Override
//    public void handleError(ClientHttpResponse response) throws IOException {
//        byte[] body = getResponseBody(response);
//        String message = getMessage(response.getRawStatusCode(),
//                response.getStatusText(), body, getCharset(response));
//        JSONParser parser = new JSONParser();
//        try {
//            Object obj = parser.parse(message.substring(6));
//            JSONArray exceptionArray = (JSONArray) obj;
//            JSONObject tempObj = (JSONObject) exceptionArray.get(0);
//            String code = (String) tempObj.get("errorCode");
//            if (code != null) {
//                String errorMessage = (String) tempObj.get("errorMessage");
//                throw new CustomException(errorMessage, code);
//            }
//        } catch (ParseException e) {
//            throw new CustomException(ErrorCode.JSON_PARSE_FAIL);
//        }
//    }
//}
