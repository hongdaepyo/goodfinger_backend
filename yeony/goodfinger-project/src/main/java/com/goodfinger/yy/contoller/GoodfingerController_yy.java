package com.goodfinger.yy.contoller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.goodfinger.yy.repository.Company;
import com.goodfinger.yy.repository.CompanyRepository;
import com.goodfinger.yy.service.GoodfingerService;
import com.goodfinger.yy.service.GoodfingerServiceImpl;

@RestController
@RequestMapping("/com")
public class GoodfingerController_yy {
	Logger log = LoggerFactory.getLogger(GoodfingerController_yy.class);

	@Autowired
	private CompanyRepository repositoryCom;
	
	@Resource(name = "GoodfingerService")
	private GoodfingerService service ;
	
	@Value("${upload.path}")
	private String filePathRoot;
	
	@CrossOrigin(origins = "*")
	@GetMapping("company")
	public ResponseEntity getCompany(@RequestParam(value = "comId") String comId) throws Exception{
		log.debug("getCompany start.");
		log.debug("comId:'" + comId + "'");
		
		Company com = service.getCompanyByComId(comId);
		if(com == null){
			return new ResponseEntity(com, HttpStatus.NO_CONTENT);
		}
		log.debug("getCompany end.");
		return new ResponseEntity(com, HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param request
	 * {
			"comid":"yycom2",
			"location":"서울시강남구",
			"mastername":"박예연",
			"name":"예연회사2"
		}
	 * @param comstring
	 * @param files
	 * @return
	 */
	@CrossOrigin(origins = "*")
	@PostMapping(value="insert", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity insertCompany(HttpServletRequest request, @RequestParam String comstring, 
			@RequestParam(value="files", required = false) ArrayList<MultipartFile> files) {
		if (comstring == null || files == null) {
			new ResponseEntity("null", HttpStatus.GONE);
		}
		JSONObject param = new JSONObject();
		param.put("comString", comstring);
		param.put("files", files);
		param.put("filePathRoot", filePathRoot);
		
		
		System.out.println("request" + request);
		System.out.println("comString" + comstring);
		
		Company com = new Company();
		Company successInsert = new Company();
		
		try {
			successInsert = service.insertCompany(param);
			if(successInsert == null){
				new ResponseEntity("error", HttpStatus.EXPECTATION_FAILED);
			}
		} catch ( Exception  e) {
			e.printStackTrace();
			new ResponseEntity("error", HttpStatus.EXPECTATION_FAILED);
		} 
		
		return new ResponseEntity(successInsert, HttpStatus.OK);
	}	
}
