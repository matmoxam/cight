//package com.Cight.CightSee.Controller;
//
//import com.Cight.CightSee.OPENAI.OPENAI;
//import org.json.JSONObject;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/image")
//public class OpenAIImageController {
//
//    private final OPENAI openAIService;
//
//    // Dependency Injection via Constructor
//    public OpenAIImageController(OPENAI openAIService) {
//        this.openAIService = openAIService;
//    }
//
//    @PostMapping("/describe")
//    public ResponseEntity<?> describeImage(@RequestParam("file") MultipartFile file) {
//        try {
//            byte[] imageBytes = file.getBytes();
//            JSONObject response = openAIService.describeImage(imageBytes);
//            return ResponseEntity.ok(response.toMap());
//        } catch (IOException | InterruptedException e) {
//            return ResponseEntity.status(500).body("Error processing image: " + e.getMessage());
//        }
//    }
//
//
//    @PostMapping("/describe-with-objects")
//    public ResponseEntity<?> describeImageWithObjects(
//            @RequestParam("file") MultipartFile file,
//            @RequestParam(value = "objects", required = false) List<String> objectsToLookFor) {
//        try {
//            byte[] imageBytes = file.getBytes();
//            JSONObject response = openAIService.describeImage(imageBytes, objectsToLookFor);
//            return ResponseEntity.ok(response.toMap());
//        } catch (IOException | InterruptedException e) {
//            return ResponseEntity.status(500).body("Error processing image: " + e.getMessage());
//        }
//    }
//}

package com.Cight.CightSee.Controller;

import com.Cight.CightSee.OPENAI.OPENAI;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import com.Cight.CightSee.OPENAI.*;
import java.io.IOException;

@RestController
@RequestMapping("/api/image")
public class OpenAIImageController {

    private final OPENAI openAIService;
    public OpenAIImageController(OPENAI openAIService) {
        this.openAIService = openAIService;
    }

    /**
     * Endpoint to handle image upload and send it to OpenAI for description.
     * The image is sent as a byte array to OpenAI API.
     *
     * @param file The image file uploaded by the user.
     * @return A response with the image description or an error message.
     */
    @PostMapping("/describe-image")
    public ResponseEntity<String> describeImage(@RequestParam("file") MultipartFile file) {
        try {
            byte[] imageBytes = file.getBytes();
            String description = null;
			try {
				description = openAIService.describeImage(imageBytes);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Call the service to process the image and get description
            
            return ResponseEntity.ok(description);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error processing the image.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
