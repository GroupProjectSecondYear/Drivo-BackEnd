package com.gp.learners.service;

import java.util.ArrayList;
import java.util.List;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.gp.learners.entities.Lesson;
import com.gp.learners.entities.Path;
import com.gp.learners.entities.StudentLesson;
import com.gp.learners.entities.SubPath;
import com.gp.learners.entities.TimeSlot;
import com.gp.learners.entities.mapObject.InstructorMap;
import com.gp.learners.entities.mapObject.LessonMap;
import com.gp.learners.repositories.InstructorRepository;
import com.gp.learners.repositories.LessonRepository;
import com.gp.learners.repositories.PackageRepository;
import com.gp.learners.repositories.PathRepository;
import com.gp.learners.repositories.StudentLessonRepository;
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
	
	@Autowired
	LessonRepository lessonRepository;
	
	@Autowired
	StudentLessonRepository studentLessonRepository;
	
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
	

	
	public List<InstructorMap> getRelevantInstructors(Integer dayId, Integer packageId ,Integer timeSlotId ,Integer pathId,Integer transmission){
		List<InstructorMap> instructorList=new ArrayList<InstructorMap>();
		if( dayId>-1 && dayId<7 && transmission>0 && transmission<3) {
			if( dayId!=null && packageId!=null && timeSlotId!=null && transmission!=null && pathId!=null) {
				if(packageRepository.existsById(packageId) && timeSlotRepository.existsById(timeSlotId) && pathRepository.existsById(pathId)) {
					List<Integer>  instructorIdList=instructorRepository.getRelevantInstructors(dayId,packageId,timeSlotId,transmission);
					
					
					for (Integer id : instructorIdList) {
						String name=instructorRepository.nameFindByInstructorId(id);
						instructorList.add(new InstructorMap(id,name));
					}
					
				}
			}
		}
		
		return instructorList;
	}
	
	//Lesson Functions
	public String addLesson(Integer dayId,Integer packageId,Integer timeSlotId,Integer pathId,Integer transmission,Integer instructorId,Integer numStudent) {
		if( dayId>-1 && dayId<7 && transmission>0 && transmission<3 && numStudent>0) {
			if(packageRepository.existsById(packageId) && timeSlotRepository.existsById(timeSlotId) && pathRepository.existsById(pathId) && instructorRepository.existsById(instructorId)) {
				Lesson lesson=new Lesson();
				lesson.setDay(dayId);
				lesson.setPackageId(packageRepository.findByPackageId(packageId));
				lesson.setTimeSlotId(timeSlotRepository.findByTimeSlotId(timeSlotId));
				lesson.setPathId(pathRepository.findByPathId(pathId));
				lesson.setTransmission(transmission);
				lesson.setInstructorId(instructorRepository.findByInstructorId(instructorId));
				lesson.setNumStu(numStudent);
				lesson.setStatus(1);
				
				if(notExistLesson(lesson)) {
					lessonRepository.save(lesson);
					return "success";
				}
				
			}
		}
		return "notsuccess";
	}
	
	
	
	public List<LessonMap> getLessonList(Integer type) {
		
		List<LessonMap> lessonList=new ArrayList<LessonMap>();
		
		for (int i=1 ; i<=7 ; i++) {
			
			
			List<Lesson> lessons= new ArrayList<Lesson>();
			if(i==7) {
				lessons=lessonRepository.getLessonASC(0,type);
			}else {
				lessons=lessonRepository.getLessonASC(i,type);
			}
			
			
			LessonMap lessonMap = new LessonMap();
			ArrayList<String> timeSlotData = new ArrayList<String>();
			ArrayList<List<String>> packageData = new ArrayList<List<String>>();
			ArrayList<List<String>> instructorData = new ArrayList<List<String>>();
			ArrayList<List<String>> pathData = new ArrayList<List<String>>();
			ArrayList<List<Integer>> numStuData = new ArrayList<List<Integer>>();;
			ArrayList<List<Integer>> idData = new ArrayList<List<Integer>>();
		
			
			List<String> packageDataRow = new ArrayList<String>();
			List<String> instructorDataRow = new ArrayList<String>();
			List<String> pathDataRow = new ArrayList<String>();
			List<Integer> numStuDataRow = new ArrayList<Integer>();
			List<Integer> idDataRow = new ArrayList<Integer>();
			
			
			
			if(lessons!=null && lessons.size()>0) {
				TimeSlot timeSlot=lessons.get(0).getTimeSlotId();
				
				for (Lesson lesson : lessons) {
					if(timeSlot.equals(lesson.getTimeSlotId())) {
						packageDataRow.add(lesson.getPackageId().getTitle()+"("+getTransmission(lesson.getTransmission())+")");
						instructorDataRow.add(lesson.getInstructorId().getStaffId().getName());
						pathDataRow.add(lesson.getPathId().getOrigin()+" : "+lesson.getPathId().getDestination());
						numStuDataRow.add(lesson.getNumStu());
						idDataRow.add(lesson.getLessonId());
						
					}else {
						
						timeSlotData.add(timeSlot.getStartTime().toString()+" : "+timeSlot.getFinishTime().toString());
						
						packageData.add(packageDataRow);
						instructorData.add(instructorDataRow);
						pathData.add(pathDataRow);
						numStuData.add(numStuDataRow);
						idData.add(idDataRow);
						
						packageDataRow = new ArrayList<String>();
						instructorDataRow = new ArrayList<String>();
						pathDataRow = new ArrayList<String>();
						numStuDataRow = new ArrayList<Integer>();
						idDataRow = new ArrayList<Integer>();
						
						packageDataRow.add(lesson.getPackageId().getTitle()+"("+getTransmission(lesson.getTransmission())+")");
						instructorDataRow.add(lesson.getInstructorId().getStaffId().getName());
						pathDataRow.add(lesson.getPathId().getOrigin()+" : "+lesson.getPathId().getDestination());
						numStuDataRow.add(lesson.getNumStu());
						idDataRow.add(lesson.getLessonId());
						
						timeSlot=lesson.getTimeSlotId();
						
					}
				}
				
				timeSlotData.add(timeSlot.getStartTime()+" : "+timeSlot.getFinishTime());
				
				packageData.add(packageDataRow);
				instructorData.add(instructorDataRow);
				pathData.add(pathDataRow);
				numStuData.add(numStuDataRow);
				idData.add(idDataRow);
				
			}else {
				packageDataRow.add("-");
				instructorDataRow.add("-");
				pathDataRow.add("-");
				numStuDataRow.add(0);
				idDataRow.add(0);
				
				timeSlotData.add("-");
				packageData.add(packageDataRow);
				instructorData.add(instructorDataRow);
				pathData.add(pathDataRow);
				numStuData.add(numStuDataRow);
				idData.add(idDataRow);
			}
			
			lessonMap.setDay(getDay(i));
			lessonMap.setTimeSlotData(timeSlotData);
			lessonMap.setPackageData(packageData);
			lessonMap.setInstructorData(instructorData);
			lessonMap.setPathData(pathData);
			lessonMap.setNumStuData(numStuData);
			lessonMap.setIdData(idData);
			
			lessonList.add(lessonMap);
		}
		return lessonList;
	}
	
	public Lesson getLesson(Integer lessonId) {
		Lesson lesson = new Lesson();
		if(lessonRepository.existsById(lessonId)) {
			lesson = lessonRepository.findByLessonId(lessonId);
		}
		return lesson;
	}
	
	public Integer deleteLesson(Integer lessonId) {
		
		if(lessonRepository.existsById(lessonId)) {
			
			//check whether this lesson already book in the future or past
			if(isLessonBook(lessonId)) {
				return 1;//can't perform dalete action
			}else {
				lessonRepository.deleteById(lessonId);
				return 0;//perform delete action
			}
		}
		return -1;//not exist lesson in the table
	}
	
	public String lessonDeactivate(Integer lessonId) {
		if(lessonRepository.existsById(lessonId)) {
			Lesson lesson=lessonRepository.findByLessonId(lessonId);
			lesson.setStatus(0);
			lessonRepository.save(lesson);
			return "success";
		}
		return "notsuccess";
	}
	
	public Integer lessonActivate(Integer lessonId) {
		if(lessonRepository.existsById(lessonId)) {
			Lesson deactivateLesson=lessonRepository.findByLessonId(lessonId);
			
			//check whether above deactivate lesson's instructor available or not
			Lesson object=lessonRepository.findByDeactivateLessonDetails(deactivateLesson.getDay(),deactivateLesson.getTimeSlotId(),deactivateLesson.getInstructorId());
			if(object != null) {//Instructor is not available.so can't activate this lesson
				return 0;
			}else {//Instructor is available.so lesson can be activate
				deactivateLesson.setStatus(1);
				lessonRepository.save(deactivateLesson);
				return 1;
			}
		}
		return -1;
	}
	
	public String updateLesson(Integer lessonId,Integer type,Integer dayId,Integer timeSlotId,Integer pathId,Integer instructorId,Integer numStudent) {
		if( dayId>-1 && dayId<7  && numStudent>0) {
			if(lessonRepository.existsById(lessonId) && timeSlotRepository.existsById(timeSlotId) && pathRepository.existsById(pathId) && instructorRepository.existsById(instructorId)) {
				Lesson lesson=lessonRepository.findByLessonId(lessonId);
				lesson.setDay(dayId);
				lesson.setTimeSlotId(timeSlotRepository.findByTimeSlotId(timeSlotId));
				lesson.setPathId(pathRepository.findByPathId(pathId));
				lesson.setInstructorId(instructorRepository.findByInstructorId(instructorId));
				lesson.setNumStu(numStudent);
				lesson.setStatus(1);
	
				lessonRepository.save(lesson);
				return "success";
		
			}
		}
		return "notsuccess";
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
	
	private Boolean notExistLesson(Lesson lesson) {
		Lesson  object=lessonRepository.findByLessonDetails(lesson.getDay(),lesson.getTransmission(),lesson.getNumStu(),lesson.getInstructorId(),lesson.getTimeSlotId(),lesson.getPackageId(),lesson.getPathId());
		if(object != null ) {
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
	
	private String getDay(Integer i) {
		if(i==1)	return "Monday";
		else if(i==2) return "Tuesday";
		else if(i==3) return "Wednesday";
		else if(i==4) return "Thursday";
		else if(i==5) return "Friday";
		else if(i==6) return "Saturday";
		return "Sunday";
	}
	
	private String getTransmission(int i) {
		if(i==1) {
			return "Manual";
		}
		return "Auto";
	}
	
	private Boolean isLessonBook(Integer lessonId) {
		StudentLesson object= studentLessonRepository.isExistByLessonId(lessonRepository.findByLessonId(lessonId));
		if(object != null) {
			return true;
		}
		return false;
	}
	
}
