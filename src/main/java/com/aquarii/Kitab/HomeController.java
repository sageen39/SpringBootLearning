package com.aquarii.Kitab;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "/images")
public class HomeController {

	private static final String FILENAME = "{filename:.+}";

	@Autowired
	private ImageService imageService;

	
	//call through terminal : curl -v http://localhost:8080/images/abstract-wallpaper-images-hiht.jpg/raw
	@RequestMapping(value = "/"+ FILENAME + "/raw", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> oneRawImage(@PathVariable String filename) {

		try {
			Resource file = imageService.findOneImage(filename);
			return ResponseEntity.ok().contentLength(file.contentLength()).contentType(MediaType.IMAGE_JPEG)
					.body(new InputStreamResource(file.getInputStream()));
		} catch (Exception e) {

			return ResponseEntity.badRequest().body("couldn't find " + filename + "=>" + e.getMessage());
		}
	}

	//create request through Terminal: curl -v -X POST -F file=@/home/lt99/Pictures/Wallpapers/abstract-wallpaper-images-hiht.jpg localhost:8080/images
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> createFile(@RequestParam("file") MultipartFile file, HttpServletRequest servletRequest){
		
		try {
			this.imageService.createImage(file);
			final URI locationURI = new URI(servletRequest.getRequestURL().toString() + "/")
					.resolve(file.getOriginalFilename() + "/raw");
			
			return ResponseEntity.created(locationURI)
					.body("Sucessfully Upload"+ file.getOriginalFilename());
			
//			return ResponseEntity.created(request.getURI().resolve(file.getOriginalFilename()+ "/raw"))
			
		} catch (Exception e) {
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Failed to upload"+file.getOriginalFilename() + "-> "+ e.getMessage());
			
		}
	}
	
	//terminal request: curl -v -X DELETE http://localhost:8080/images/abstract-wallpaper-images-hiht.jpg
	@RequestMapping(method = RequestMethod.DELETE, value = "/"+ FILENAME)
	@ResponseBody
	public ResponseEntity<?> deleteFile(@PathVariable String filename){
		
		try {
			this.imageService.deleteImage(filename);
			return ResponseEntity.status(HttpStatus.NO_CONTENT)
					.body("Sucessfully Deleted :"+ filename);
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Failed to Delete "+ filename + "=>" + e.getMessage());
		}
	}
	
}
