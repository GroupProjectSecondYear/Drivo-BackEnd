package com.gp.learners.controllers;



import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.gp.learners.entities.Video;
import com.gp.learners.repositories.VideoRepository;
import com.gp.learners.service.VideoService;

@RestController
@CrossOrigin(origins="*",allowedHeaders="*",maxAge=3600)
public class VideoController {

	@Autowired
	VideoService videoService;

	@Autowired
	VideoRepository videoRepository;

	@GetMapping("/videos")
	public List<Video> getVideoList(){
		return videoService.getVideoList();
	}

	@GetMapping("video/{videoId}")
	public ResponseEntity<Video> getVideo(@PathVariable("videoId") Integer videoId) {
		Video video=videoService.getVideoByID(videoId);
		if(video.getVideoId() != null) {
			return ResponseEntity.ok(video);
		}
		return ResponseEntity.noContent().build();
	}

	//add video
		@PostMapping("video/add")
		public ResponseEntity<Video> addVideo(@RequestBody Video video) {
			//System.out.println("stuId:"+stuId+" object:"+object);

			Video video1=videoService.addVideo(video);
			System.out.println(video1.getVideoId());
			if(video1.getVideoId() != null) {
				return ResponseEntity.ok(video1);
			}
			return ResponseEntity.noContent().build();
		}

		//delete Video
		@DeleteMapping("video/delete/{videoId}")
		public ResponseEntity<Void> deleteVideo(@PathVariable("videoId") Integer videoId){
			System.out.println("Delete Video");
			String reply=videoService.deleteVideo(videoId);
			if(reply.equals("success")) {
				return ResponseEntity.noContent().build();
			}
			return ResponseEntity.notFound().build();
		}
		//update student Details
		@PutMapping("video/update")
		public ResponseEntity<Video> updateVideo(@Valid @RequestBody Video object){
			System.out.println("In Video Update Controller");
			Video video=videoService.updateVideo(object);
			if(video.getVideoId() != null) {
				return ResponseEntity.ok(video);
			}
			return ResponseEntity.notFound().build();
		}


}