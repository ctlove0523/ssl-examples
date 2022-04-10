package io.github.ctlove0523.ssl.tomcat;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TomcatController {

    @RequestMapping(value = "/v1/tomcats", method = RequestMethod.GET)
    public ResponseEntity<TomcatInfo> showTomcatInfo() {
        TomcatInfo tomcatInfo = new TomcatInfo();
        tomcatInfo.setName("tomcat");
        tomcatInfo.setVersion("8");

        return new ResponseEntity<>(tomcatInfo, HttpStatus.OK);
    }
}
