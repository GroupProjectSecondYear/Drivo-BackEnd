package com.gp.learners.service;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.transaction.Transactional;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import com.amazonaws.util.IOUtils;
import com.gp.learners.entities.Attendance;
import com.gp.learners.entities.User;
import com.gp.learners.entities.YearUpdate;
import com.gp.learners.repositories.AdminRepository;
import com.gp.learners.repositories.AttendanceRepository;
import com.gp.learners.repositories.FuelPaymentRepository;
import com.gp.learners.repositories.SalaryRepository;
import com.gp.learners.repositories.StaffLeaveRepository;
import com.gp.learners.repositories.UserRepository;
import com.gp.learners.repositories.YearUpdateRepository;
import com.smattme.MysqlExportService;

@Service
@EnableScheduling
public class SystemUpdateService {
	
	@Autowired
	TimeTableService timeTableService;
	
	@Autowired
	YearUpdateRepository yearUpdateRepository;
	
	@Autowired
	AdminRepository adminRepository;
	
	@Autowired 
	UserRepository userRepository;
	
	@Autowired
	S3Service s3Service;
	
	@Value("${spring.datasource.url}")
	private String dataBaseName;
	
	@Value("${spring.datasource.username}")
	private String dataBaseUserName;
	
	@Value("${spring.datasource.password}")
	private String dataBasePassword;
	
	@Value("${spring.mail.host}")
	private String emailHost;
	
	@Value("${spring.mail.username}")
	private String emailUserName;
	
	@Value("${spring.mail.password}")
	private String emailPassword;
	
	@Value("${spring.mail.port}")
	private String emailPort;
	
	@Value("${aws.s3.bucket.database_month_backup}")
	private String bucketName;
	
	@Autowired
	SalaryRepository salaryRepository;
	
	@Autowired
	FuelPaymentRepository fuelPaymentRepository;
	
	@Autowired
	StaffLeaveRepository staffLeaveRepository;
	
	@Autowired
	AttendanceRepository attendanceRepository;
	
	
	
	//check Updates yearly January 5
	//@Scheduled(cron="0 0 23 5 1  *")
	@Transactional
	public void checkAnnualUpdate() {
		
		//get current year
		LocalDate currentDate = timeTableService.getLocalCurrentDate();
		Integer year = currentDate.getYear();
		
		//1.Get db instance and save it
		Boolean reply = monthlyDatabseBackup();
		if(reply) {//Backup success
			//2.Update the db
			
			//i)Update Salary Table
			List<Integer> salaryIdList = salaryRepository.findsalaryIdByYear(year-1);
			for (Integer salaryId : salaryIdList) {
				salaryRepository.deleteById(salaryId);
			}
			
			//ii)Update Fuel Table
			List<Integer> fuelPaymenIdtList = fuelPaymentRepository.findsalaryIdByYear(year-1);
			for (Integer fuelId : fuelPaymenIdtList) {
				fuelPaymentRepository.deleteById(fuelId);
			}
			
			//iii)Staff Leave Table
			List<Integer> staffLeaveIdList = staffLeaveRepository.findStaffLeaveIdByYear(year-1);
			for (Integer staffLeaveId : staffLeaveIdList) {
				staffLeaveRepository.deleteById(staffLeaveId);
			}
			
			//iv)Attendance Table
			List<Integer> attendanceIdList = attendanceRepository.findAttendanceIdByYear(year-1);
			for (Integer attendanceId : attendanceIdList) {
				attendanceRepository.deleteById(attendanceId);
			}
			
			//update year_update record
			YearUpdate object = yearUpdateRepository.getLastRecord();
			if(object.getUpdateYear()==(year-1)) {
				object.setSystemUpdateStatus(1);
				yearUpdateRepository.save(object);
			}
		}
	}
	
	
	//getUpdate Message
	public YearUpdate getUpdateMessage() {
		return yearUpdateRepository.getLastRecord();
	}
	
	//Run every month first date
	//@Scheduled(cron="0 0 0 1 1/1  *")
	//@Scheduled(cron="0 0/1 * 1/1 *  *")
    public Boolean monthlyDatabseBackup() {
		
		Boolean flag =false;

		Properties properties = new Properties();
		properties.setProperty(MysqlExportService.DB_NAME, "leanersnew");
		properties.setProperty(MysqlExportService.DB_USERNAME, dataBaseUserName);
		properties.setProperty(MysqlExportService.DB_PASSWORD, dataBasePassword);
		properties.setProperty(MysqlExportService.TEMP_DIR, new File("external").getPath());
		

		properties.setProperty(MysqlExportService.TEMP_DIR, new File("external").getPath());
		
		properties.setProperty(MysqlExportService.PRESERVE_GENERATED_ZIP, "true");
		MysqlExportService mysqlExportService = new MysqlExportService(properties);
		
		

		try {
			mysqlExportService.export();
			File file = mysqlExportService.getGeneratedZipFile();
			String fileName = file.getName();
			
			MultipartFile multiPartFile = getMultipartFile(file);
			if(multiPartFile!=null) {
				String reply = uploadBackupDBFile(multiPartFile, fileName);
				if(reply.equals("success")) {
					flag=true;
					System.out.println("Database Backup successful on "+file.getName());
				}
				
			}
			return true;
		} catch (Exception e) {
			System.out.println("There is a problem of monthly backup database service");
		}
		
		if(flag) {
			//send message for admin backup success
		}else {
			//send email backup not success
		}
		
		return false;
    }
	
	public MultipartFile getMultipartFile(File file) {
	    try {
	    	FileInputStream input = new FileInputStream(file);
		    MultipartFile multipartFile = new MockMultipartFile("file",
		            file.getName(), "text/plain", IOUtils.toByteArray(input));
		    return multipartFile;
		} catch (Exception e) {
			System.out.println("Problem in MultipartFile conversion");
		}
	    return null;
	}
	
	
	public String uploadBackupDBFile(MultipartFile file,String fileName) {	
			String keyName = fileName;
			if(keyName!=null) {
				s3Service.uploadFile(keyName, file,bucketName);
				return "success";
			}		
		return null;
	}
	
}
