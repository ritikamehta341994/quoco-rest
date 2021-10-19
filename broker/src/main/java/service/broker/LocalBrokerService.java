package service.broker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import service.core.ClientApplication;
import service.core.ClientInfo;
import service.core.Quotation;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * Implementation of the broker service that uses the Service Registry.
 * 
 * @author Rem
 *
 */
@RestController
public class LocalBrokerService{

	static Random r = new Random();
	private static Map<Integer,ClientApplication> clientApplications= new HashMap<>();
	private static ArrayList<ClientApplication> clientApplicationArrayList = new ArrayList<>();

	@Value("${auldfellasurl}")
	String auldfellas ;
	@Value("${dodgydriversurl}")
	String dodgydrivers ;
	@Value("${girlpowerurl}")
	String girlpower ;

	@RequestMapping(value="/application",method= RequestMethod.POST)
	public ResponseEntity<ClientApplication> callGetQuotation(@RequestBody ClientInfo info) throws URISyntaxException {
		ArrayList<Quotation> quotationFromServices = getQuotations(info);
		ClientApplication clientApplication = new ClientApplication();
		clientApplication.setClientInfo(info);
		clientApplication.setApplicationNumber(r.nextInt());
		clientApplication.setQuotations(quotationFromServices);
		clientApplications.put(clientApplication.getApplicationNumber(), clientApplication);
		String path = ServletUriComponentsBuilder.fromCurrentContextPath().
				build().toUriString()+ "/application/"+clientApplication.getApplicationNumber();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(new URI(path));
		clientApplicationArrayList.add(clientApplication);
		return new ResponseEntity<>(clientApplication,headers,HttpStatus.CREATED);
	}

	@RequestMapping(value="/application/{applicationnumber}",method=RequestMethod.GET)
	public ClientApplication getBasedOnApplicationNumber(@PathVariable("applicationnumber") String applicationNumber) {
		ClientApplication clientApplication = clientApplications.get(applicationNumber);
		if (clientApplication == null) throw new NoSuchClientAppplicationException();
		return clientApplication;
	}

	@RequestMapping(value="/application",method=RequestMethod.GET)
	public ArrayList<ClientApplication> getAllClientApplication() {
		return clientApplicationArrayList;
	}

	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public class NoSuchClientAppplicationException extends RuntimeException {
		static final long serialVersionUID = -6516152229878843037L;
	}

	public ArrayList<Quotation> getQuotations(ClientInfo info) {
		ArrayList<Quotation> quotations = new ArrayList<>();
		ArrayList<String> urls = new ArrayList<>();

		urls.add(auldfellas);
		urls.add(dodgydrivers);
		urls.add(girlpower);

		for(String url : urls){
			RestTemplate restTemplate = new RestTemplate();
			HttpEntity<ClientInfo> request = new HttpEntity<>(info);
			Quotation quotation =
					restTemplate.postForObject(url,
							request, Quotation.class);
			quotations.add(quotation);
		}
		return quotations;
	}
}
