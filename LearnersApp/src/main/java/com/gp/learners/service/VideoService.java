package com.gp.learners.service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gp.learners.entities.Video;
import com.gp.learners.entities.User;
import com.gp.learners.repositories.VideoRepository;

@Service
public class VideoService {

	@Autowired
	VideoRepository videoRepository;
	
	@Autowired
	S3Service s3Service;
	
	@Value("${aws.s3.bucket.learning_material_video}")
	private String bucketName; // bucket name should be changed

	// getVideoList
	public List<Video> getVideoList() {
		System.out.println("IN videorepo");
		List<Video> videoList = videoRepository.findAll();
		// Video video1=videoList.get(0);
		return videoList;
	}

	public Video getVideoByID(Integer videoId) {
		if (videoId != null) {
			if (videoRepository.existsById(videoId)) {
				return videoRepository.getVideoById(videoId);
			}
		}
		return new Video();
	}

	// Add Video
	public Video addVideo(Video video) {

		Video result = videoRepository.save(video);
		if (result != null)
			return result;
		
		else
			return null;
	}

	// delete Video
	public String deleteVideo(Integer videoId) {
		System.out.println("Delete Video Serv");
		if (videoId != null) {
			if (videoRepository.existsById(videoId)) {
				videoRepository.deleteById(videoId);
				return "success";
			}
		}
		return "error";
	}

	// update Student Details
	public Video updateVideo(Video video) {
		if (videoRepository.existsById(video.getVideoId())) {
			// Video video1=videoRepository.getVideoById(video.getVideoId());
			return videoRepository.save(video);
		}

		return new Video();
	}
	public String uploadVideo(MultipartFile file, Integer videoId) {
		System.out.println("Video UPLOADING");
		if (videoRepository.existsById(videoId)) {
			String keyName = videoId + ".video";
			if (keyName != null) {
				s3Service.uploadFile(keyName, file, bucketName);
				Video video = videoRepository.getVideoById(videoId);
				//user.setProfileImage(1);
				//userRepository.save(user);
				System.out.println("Video UPLOADING success");
				return "success";
			}
		}
		return null;
	}
	public ByteArrayOutputStream downloadVideo(Integer videoId) {
		if (videoRepository.existsById(videoId)) {
			System.out.println("Video dwnlding Byte Array Method");
			String keyName = videoId + ".video";
			
			if (videoId != null) {
				Video video = videoRepository.getVideoById(videoId);
				try {
					System.out.println("Video dwnLOADING Byte Array Method try chtch");
					/*if (video.getProfileImage() == 1) {
						return s3Service.downloadFile(keyName, bucketName);
					} else {*/
					return s3Service.downloadFile(keyName, bucketName);
					/*}*/
				} catch (Exception e) {
					System.out.println("There is a problem in s3 bucket");
				}

			}
		}
		return null;
	}

}