package com.example.security;
import com.example.model.Token;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;
import java.util.*;

public class SessionGuardianImpl implements SessionGuardian{
	
	final private String SECRET =  "6736f452836b669273f4a520b9a0ac35";
	@Override
	public String createSession(String userid, String username) {
		
		String token = null;
		String message = null;
		
		try {	
				String header = "{alg:HS256,type:JWT}";
				Long tomorrow =System.currentTimeMillis()+86400000;
				String expire = tomorrow + "";
				
				String payload = "{userid:"+userid+",username:"+username+",expire:"+expire+"}";
				
				Mac hasher = Mac.getInstance("HmacSHA256");
			    hasher.init(new SecretKeySpec(SECRET.getBytes(), "HmacSHA256"));
			    
			    header = Base64.getEncoder().encodeToString(header.getBytes());
			    payload = Base64.getEncoder().encodeToString(payload.getBytes());
			    
			    message = header+"."+payload;
			    
			    
			    byte[] hash = hasher.doFinal(message.getBytes());
			    
			    // to base64
			    token = Base64.getEncoder().encodeToString(hash);

			  }
			  catch (NoSuchAlgorithmException e) {}
			  catch (InvalidKeyException e) {}
		
		System.out.println("Token is created:"+message+"."+token);
		System.out.println("CreateSession methodu testi!--INFO--> Token doğru oluştuğu kontrol edilir.");
		return message+"."+token;
	}

	@Override
	public Token controlSession(String token) {
		
		System.out.println("token:"+token);
		
		String finalToken = null;
		String[] arr = token.split("\\."); 
		try {	
		
			String message = arr[0]+"."+arr[1];
			
			Mac hasher = Mac.getInstance("HmacSHA256");
		    hasher.init(new SecretKeySpec(SECRET.getBytes(), "HmacSHA256"));
		    
		    byte[] hash = hasher.doFinal(message.getBytes());
		    
		    // to base64
		    finalToken = Base64.getEncoder().encodeToString(hash);
		    
		    System.out.println("System Created Token:"+finalToken);
		    System.out.println("User Sended Token:"+arr[2]);
		  }
		  catch (NoSuchAlgorithmException e) {}
		  catch (InvalidKeyException e) {}
		
		
		if(finalToken.equals(arr[2])) {
			//token is right!
			//open payload and return token object!
			
			Token userToken = new Token();
			
			System.out.println("Tokens are matching!");
			
			String decoded = new String(Base64.getDecoder().decode(arr[1]));
			arr[1] = decoded;
			System.out.println("Token Payload after decode:"+arr[1]);
			arr[1] = arr[1].substring(1,arr[1].length()-1);
			System.out.println("Token Payload substring:"+arr[1]);
			String splittedPayload[] = arr[1].split("\\,");
			userToken.setUserid(splittedPayload[0].split("\\:")[1]);
			userToken.setUsername(splittedPayload[1].split("\\:")[1]);
			
			if(Long.parseLong(splittedPayload[2].split("\\:")[1]) < System.currentTimeMillis()) {
				
				System.out.println("Token Expired!");
				
				return null;
			}
			else
				return userToken;
			
		}
		else
			return null;
	}

	@Override
	public String sanitize(String input) {
		  return input.replaceAll("/<(|\\/|[^>\\/bi]|\\/[^>bi]|[^\\/>][^>]+|\\/[^>][^>]+)>/g", "");
	}

	@Override
	public boolean nameControl(String input) {
		if(input.length()>100 || input.length()==0)
			return false;
		else
			return true;
	}

	@Override
	public boolean usernameControl(String input) {
		if(input.length()>50 || input.length()==0)
			return false;
		else
			return true;
	}

}
