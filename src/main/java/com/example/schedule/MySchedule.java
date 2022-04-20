package com.example.schedule;

import java.io.IOException;
import java.util.Date;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.repository.ProductCountRepository;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


// view와 데이터가 담긴 컨트롤러 사이에서 중간 역할 
@Component
public class MySchedule {

	@Autowired ProductCountRepository pcRepository;
	
	
	
//	@Scheduled(cron = "*/10 * * * * *")
	public void printData1() throws IOException {
		final String URL = "http://127.0.0.1:9090/ROOT/api/productcount/selectcount.json?no=1009";
		OkHttpClient client = new OkHttpClient();
		
		// import okhttp3
		Request request = new Request.Builder().url(URL).build();
		
		Response response = client.newCall(request).execute();
		String msg = response.body().string();	// String 타입으로 응답
		System.out.println(msg);
		
		// 스트링 타입의 메세지를 JSON형식으로 바꿔줌
		JSONObject jobj = new JSONObject(msg);
		System.out.println(jobj.getLong("result"));
		System.out.println(jobj.getLong("status"));
		
//		ProductCountEntity p = new ProductCountEntity();
//		pcRepository.save(p);
		
	}
	
	
	// node에서는 크론탭
	// 서버가 중지되지 않는한 계속 돌아감
//	@Scheduled(cron = "*/10 * * * * *")
	public void printData() {
		Date date = new Date();
		System.out.println(date.toString());
	}
	
}
