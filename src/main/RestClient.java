package main;
import java.io.IOException;






import org.apache.commons.codec.binary.Base64;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import exceptions.CustomExceptionHandler;
import exceptions.UnknownHttpErrorException;
import resources.CustomerCollectionRepresentation;


public class RestClient 
{
	public static void main(String[] args) throws IOException
	{
		try
		{
			RestTemplate template = new RestTemplate();		
			template.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
			template.setErrorHandler(new CustomExceptionHandler(template));

			String originalUserPass = "rac:secret";
			
			
			
			String encodedData = new String(Base64.encodeBase64(originalUserPass.getBytes()));
			
			System.out.println(encodedData);
		
			
			HttpHeaders headers = new HttpHeaders();
			
			headers.add("Authorization", "Basic " + encodedData);
			
			HttpEntity<CustomerCollectionRepresentation> request = new HttpEntity<CustomerCollectionRepresentation> (headers);
			
			
			ResponseEntity<CustomerCollectionRepresentation> reponse = template.exchange("http://localhost:8081/crm/rest/customers?fullDetails=true", HttpMethod.GET, request, CustomerCollectionRepresentation.class);
			
			System.out.println(encodedData);

			CustomerCollectionRepresentation allCustomers = reponse.getBody();
			
			Link link = allCustomers.getLink("next");
			System.out.println("the next page will be at " + link);
					
			System.out.println(allCustomers);
			
			
			//second call
			
			 template.getForObject("http://localhost:8081/crm/rest/customers?fullDetails=true", CustomerCollectionRepresentation.class);

		}
		catch(UnknownHttpErrorException e)
		{
			System.out.println("Http Status"+e.getStatusCode()+" "+e.getStatusCode().getReasonPhrase());
		}
		
	}
}
