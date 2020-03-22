package microservice.scalingmanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ScalingController {

    @Autowired
    private ScalingCommandExecutor scalingCommandExecutor;

    @RequestMapping("/scaleup")
    public ResponseEntity<String> scaleUp(String microserviceName) {
        try {
            scalingCommandExecutor.scaleUp(microserviceName);
            return ResponseEntity.ok("OK");
        } catch (Exception e) {
            return new ResponseEntity<>("Could not execute " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping("/scaledown")
    public ResponseEntity<String> scaleDown(String microserviceName, Long pid) {
        try {
            scalingCommandExecutor.scaleDown(microserviceName, pid);
            return ResponseEntity.ok("OK");
        } catch (Exception e) {
            return new ResponseEntity<>("Could not execute " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
