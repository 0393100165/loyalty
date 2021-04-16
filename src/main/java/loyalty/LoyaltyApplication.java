package loyalty;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LoyaltyApplication implements CommandLineRunner {

	@Value("${upload-dir}")
	private String imageDir;
	
	@Value("${clean-image}")
	private Boolean isCleanImage;

	public static void main(String[] args) {
		SpringApplication.run(LoyaltyApplication.class, args);
	}
	
	
	@Override
	public void run(String... args) throws Exception {
		
		if(!isCleanImage) {
			return;
		}
		
		try {
			System.out.println("Clean directory starting");
			FileUtils.cleanDirectory(new File(imageDir));
			System.out.println("Clean directory success");
		} catch (IOException e) {
			System.out.println("Clean unsuccess: " + e.getMessage());
		} catch (IllegalArgumentException e) {
			System.out.println("Directory dosen't exist. Cancel clean!!!");
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}

	}

}
