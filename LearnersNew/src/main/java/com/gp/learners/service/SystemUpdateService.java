package com.gp.learners.service;



import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.util.Properties;

import javax.transaction.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import com.amazonaws.util.IOUtils;
import com.gp.learners.entities.Admin;
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
	private void checkAnnualUpdate() {
		
		//get current year
		LocalDate currentDate = timeTableService.getLocalCurrentDate();
		Integer year = currentDate.getYear();
		
		//1.Get db instance and save it
		Boolean reply = monthlyDatabseBackup();
		if(reply) {//Backup success
			YearUpdate object = new YearUpdate();
			
			Admin admin = adminRepository.findAll().get(0);
			if(admin!=null) {
				object.setAdminId(admin);
				object.setUpdateYear(year-1);
				yearUpdateRepository.save(object);
			}
		}
	}
	

	
	//Run every month first date
	//@Scheduled(cron="0 0 0 1 1/1  *")
	//@Scheduled(cron="0 0/1 * 1/1 *  *")
    private Boolean monthlyDatabseBackup() {
		
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
	
	private MultipartFile getMultipartFile(File file) {
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
	
	
	private String uploadBackupDBFile(MultipartFile file,String fileName) {	
			String keyName = fileName;
			if(keyName!=null) {
				s3Service.uploadFile(keyName, file,bucketName);
				return "success";
			}		
		return null;
	}
	
}
