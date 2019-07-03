package com.wy.yubiao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ThreadApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThreadApplication.class, args);
		/*ExecutorService executorService = new ThreadPoolExecutor(5,10,5, TimeUnit.SECONDS,new LinkedBlockingDeque<>());
		executorService.execute(()->{

		});
		String filePath = "";
		File file = new File(filePath);
		if (file.isDirectory()){
			System.out.println("文件夹");
			String[] list = file.list();
			for (int i=0; i< list.length)
		}*/
	}

}
