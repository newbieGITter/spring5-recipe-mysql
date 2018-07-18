package com.saahas.demo.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.saahas.demo.commands.RecipeCommand;
import com.saahas.demo.services.ImageService;
import com.saahas.demo.services.RecipeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ImageController {

	private ImageService imageService;
	private RecipeService recipeService;
	
	
	public ImageController(ImageService imageService, RecipeService recipeService) {
		this.imageService = imageService;
		this.recipeService = recipeService;
	}
	
	@GetMapping("/recipe/{recipeId}/image")
	public String getRecipeImage(@PathVariable Long recipeId, Model model) throws Exception {
		model.addAttribute("recipe", recipeService.getRecipeCommandById(recipeId));
		
		return "recipe/imageuploadform";
	}
	
	@PostMapping("/recipe/{recipeId}/image")
	public String saveRecipeImage(@PathVariable Long recipeId, @RequestParam("imagefile") MultipartFile file){
		log.info("Inside saveRecipeImage method. RecipeId: " + recipeId);
		imageService.saveImageFile(recipeId, file);
		
		return "redirect:/recipe/" + recipeId + "/show";
	}
	
	@GetMapping("recipe/{recipeId}/recipeimage")
	public void renderImageFromDB(@PathVariable Long recipeId, HttpServletResponse response) throws Exception{
		RecipeCommand recipeCommand = recipeService.getRecipeCommandById(recipeId);
		
		if(recipeCommand.getImage() != null) {
			byte[] byteArr = new byte[recipeCommand.getImage().length];
			int i = 0;
			
			for(Byte wrappedByte: recipeCommand.getImage()){
				byteArr[i++] = wrappedByte;
			}
			
            response.setContentType("image/jpeg");

			InputStream inputStream = new ByteArrayInputStream(byteArr);
			
			IOUtils.copy(inputStream, response.getOutputStream());
		}
	}
	

}
