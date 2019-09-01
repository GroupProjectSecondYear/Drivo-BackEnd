package com.gp.learners.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gp.learners.entities.Instructor;
import com.gp.learners.entities.Path;
import com.gp.learners.entities.SubPath;
import com.gp.learners.entities.TimeSlot;
import com.gp.learners.repositories.InstructorRepository;
import com.gp.learners.repositories.PackageRepository;
import com.gp.learners.repositories.PathRepository;
import com.gp.learners.repositories.SubPathRepository;
import com.gp.learners.repositories.TimeSlotRepositroy;

@Service
public class TimeTableService {
	
	@Autowired
	TimeSlotRepositroy timeSlotRepository;
	
	@Autowired
	PackageRepository packageRepository;
	
	@Autowired
	PathRepository pathRepository;
	
	@Autowired
	InstructorRepository instructorRepository;
	
	@Autowired 
	SubPathRepository subPathRepository;
	
	//Time Slot functions
	public List<TimeSlot> getTimeSlotList(){
		List<TimeSlot> timeSlotList=timeSlotRepository.findAll();
		if(timeSlotList != null) {
			return timeSlotList;
		}
		return new ArrayList<TimeSlot>();
	}
	
	public String updateTimeSlot(TimeSlot timeSlot) {
		if(timeSlot.getTimeSlotId() != null && timeSlot.getTimeSlotId()>0) {
			if(timeSlotRepository.existsById(timeSlot.getTimeSlotId())) {
				timeSlotRepository.save(timeSlot);
				return "success";
			}
		}
		return "notsuccess";
	}
	
	public String addTimeSlot(TimeSlot timeSlot) {
		if(notExistFromAndTo(timeSlot)) {
			timeSlotRepository.save(timeSlot);
			return "success";
		}
		return "notsuccess";
	}
	
	public String deleteTimeSlot(Integer timeSlotId) {
		if(timeSlotId != null && timeSlotId>0) {
			if(timeSlotRepository.existsById(timeSlotId)) {
				timeSlotRepository.deleteById(timeSlotId);
				return "success";
			}
		}
		return "notsuccess";
	}
	
	//Path Functions
	public Integer addPath(Path object) {
		if(notExistPath(object)) {
			Path path=pathRepository.save(object);
			return path.getPathId();
		}
		return 0;
	}
	
	public String deletePath(Integer pathId) {
		if(pathRepository.existsById(pathId)) {
			pathRepository.deleteById(pathId);
			return "success";
		}
		return "notsuccess";
	}
	
	public Integer updatePath(Path object) {
		if(pathRepository.existsById(object.getPathId())) {
			pathRepository.save(object);
			return object.getPathId();
		}
		return 0;
	}
	
	//subPath functions
	public String addSubPaths(Integer pathId,ArrayList<String> subPaths,Integer type) {
		if(pathRepository.existsById(pathId)) {
			Boolean flag=false;
			Path path=pathRepository.findByPathId(pathId);
			
			if(type==2) {//when update perform
				deleteSubPaths(path);
			}
			
			for (String name : subPaths) {
				if(name!=null && name.trim().length()>0) {
					SubPath subPath=new SubPath();
					subPath.setName(name);
					subPath.setPathId(path);
					subPathRepository.save(subPath);
					flag=true;
				}
			}
			if(flag) {//If at least one sub path inserted
				return "success";
			}
		}
		return "notsuccess";
	}
	
	public List<String> getSubPathList(Integer pathId){
		
		if(pathRepository.existsById(pathId)) {
			List<String> subPathList=subPathRepository.findByPathId(pathRepository.findByPathId(pathId));
			if(subPathList != null) {
				return subPathList;
			}
		}
		
		return new ArrayList<String>();
	}
	
	public List<Path> getPathList(){
		List<Path> pathList = pathRepository.findAll();
		if(pathList != null ) {
			return pathList;
		}
		return new ArrayList<Path>();
	}
	

	
	public String getRelevantInstructors(Integer dayId, Integer packageId ,Integer timeSlotId ,Integer transmission){
		if( dayId!=null && packageId!=null && timeSlotId!=null && transmission!=null) {
			if(packageRepository.existsById(packageId) && timeSlotRepository.existsById(timeSlotId)) {
				List<Instructor> instructors=instructorRepository.getRelevantInstructors(dayId,packageRepository.findByPackageId(packageId).getPackageId(),timeSlotRepository.findByTimeSlotId(timeSlotId).getTimeSlotId(),transmission);
				System.out.println("ok");
				System.out.println(instructors);
			}
		}
		return "ok";
	}
	
	
	//Helping Function
	
	//check whether record exist or not in the database
	private Boolean notExistFromAndTo(TimeSlot timeSlot) {
		TimeSlot object=timeSlotRepository.findByStartTimeandFinishTime(timeSlot.getStartTime(),timeSlot.getFinishTime());
		if(object != null) {
			return false;
		}
		return true;
	}
	
	private Boolean notExistPath(Path path) {
		Path object=pathRepository.findByPathDetails(path.getPathName(),path.getOrigin(),path.getDestination());
		if(object != null) {
			return false;
		}
		return true;
	}
	
	private void deleteSubPaths(Path pathId) {
		List<Integer> subPathIdList=subPathRepository.findByPathIdAndGetIds(pathId);
		if(subPathIdList != null) {
			for (Integer subPathId : subPathIdList) {
				subPathRepository.deleteById(subPathId);
			}
		}
	}
	
}
