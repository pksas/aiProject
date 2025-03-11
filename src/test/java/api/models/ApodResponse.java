/*
пример сайта с openApi без регестрации
 */
/*
напиши пару тестов RestAssured и Lombok, по автоматизации тестирования
https://api.nasa.gov/
 */

package api.models;

import lombok.Data;

@Data
public class ApodResponse {
    private String copyright;
    private String date;
    private String explanation;
    private String hdurl;
    private String media_type;
    private String service_version;
    private String title;
    private String url;
}