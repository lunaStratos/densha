package ikuzo.kimi.densha;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xml.sax.InputSource;

import ikuzo.kimi.densha.dao.SubwayDAO;
import ikuzo.kimi.densha.example.SeatAndHuman;
import ikuzo.kimi.densha.example.realSeatAndHuman;
import ikuzo.kimi.densha.example.realSeatAndHumanJson;
import ikuzo.kimi.densha.util.apiMake;
import ikuzo.kimi.densha.vo.Subway;

@Controller
public class apiController {

	@Autowired
	SubwayDAO dao;

	// api페이지 
	@RequestMapping(value = "/api", method = RequestMethod.GET)
	public String api(Model model) { // api 안내 페이지

		JSONObject json = apiModule("2002");
		Document xml = apiModuleXML("2002");

		String xmlString = XMLToString(xml);

		model.addAttribute("objJson", json);
		model.addAttribute("objXml", xmlString);

		return "api";
	}

	// json형식으로 api 만드는 기능, GET형식으로 한다. 
	@ResponseBody
	@RequestMapping(value = "/apiservice/json", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public String apiserviceJSON(Model model, String subwaynum, ServletResponse res) { // api 안내 페이지
		// http://localhost:8888/densha/apiservice/sample?subwaynum=2002/
		JSONObject obj = apiModule(subwaynum);
		res.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=utf-8");
		return obj.toString();
	}

	
	//XML 형식으로 api 만드는 기능, GET형식으로 한다. 
	@ResponseBody
	@RequestMapping(value = "/apiservice/xml", method = RequestMethod.GET, produces = "application/xml; charset=utf-8")
	public String apiserviceXML(Model model, String subwaynum) throws Exception {
		// http://localhost:8888/densha/apiservice/sample?subwaynum=2002/
		Document xml = apiModuleXML(subwaynum);
		String str = XMLToString(xml);
		return str;

	}
	
	// 창에서 입력받아서 만들어주는 예시, textarea에 표시 
	@ResponseBody
	@RequestMapping(value = "/example", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public String example(String carnum) throws Exception {
		// http://localhost:8888/densha/apiservice/sample?subwaynum=2002/
		
		apiMake api = new apiMake();
		Document xml = apiModuleXML(carnum);
		String str = XMLToString(xml);
		return str;

	}
	
	// 테스트페이지, USER들은 안씀 
	@RequestMapping(value = "/testDrive", method = RequestMethod.GET, produces = "application/xml; charset=utf-8")
	public String testDrive(String carnum, Model model) throws Exception {
		
		realSeatAndHuman realApiXml = new realSeatAndHuman();
		realSeatAndHumanJson realApiJson = new realSeatAndHumanJson();
		ArrayList <SeatAndHuman> CarSeatHumanListXml= realApiXml.getSeatAndHuman(carnum);
		String CarSeatHumanListJson = realApiJson.getSeatAndHuman(carnum);
		
//		model.addAttribute("carSeatList", CarSeatHumanListXml);
		model.addAttribute("carSeatList", CarSeatHumanListJson);
		
		
		return "testDrive";

	}
	
	// json으로  api 만드는 메소드,
	public JSONObject apiModule(String subwaynum) {
		ArrayList<Subway> subwayList = new ArrayList<>();

		subwayList = dao.selectSubwayArray(subwaynum);

		int allHuman = 0; // 모든 인원
		JSONObject obj = new JSONObject();
		obj.put("subwayNum", subwayList.get(0).getSubwayNum());
		obj.put("allhuman", allHuman); // 총인원
		try {
			JSONArray jArray = new JSONArray();// 배열이 필요할때
			
			for (int i = 0; i < subwayList.size(); i++)// 배열
			{
				JSONObject sObject = new JSONObject();// 배열 내에 들어갈 json
				
				sObject.put("subwayNum", subwayList.get(i).getSubwayNum());
				sObject.put("carNum", subwayList.get(i).getCarNum());
				sObject.put("humanNum", subwayList.get(i).getHumanNum());
				double percent = Math.round(Double.parseDouble(subwayList.get(i).getHumanNum())/160*100);
				sObject.put("humanPercent", percent);
				allHuman += Integer.parseInt(subwayList.get(i).getHumanNum());
				// 총인원 구하는 부분

				sObject.put("elderlySeat1", subwayList.get(i).getElderlySeat1());
				sObject.put("elderlySeat2", subwayList.get(i).getElderlySeat2());
				sObject.put("elderlySeat3", subwayList.get(i).getElderlySeat3());
				sObject.put("elderlySeat4", subwayList.get(i).getElderlySeat4());
				sObject.put("elderlySeat5", subwayList.get(i).getElderlySeat5());
				sObject.put("elderlySeat6", subwayList.get(i).getElderlySeat6());
				sObject.put("elderlySeat7", subwayList.get(i).getElderlySeat7());
				sObject.put("elderlySeat8", subwayList.get(i).getElderlySeat8());
				sObject.put("elderlySeat9", subwayList.get(i).getElderlySeat9());
				sObject.put("elderlySeat10", subwayList.get(i).getElderlySeat10());
				sObject.put("elderlySeat11", subwayList.get(i).getElderlySeat11());
				sObject.put("elderlySeat12", subwayList.get(i).getElderlySeat12());
				
				jArray.put(sObject);
			}

			obj.put("item", jArray);// 배열을 넣음

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return obj;
	}
	
	// XML api 만드는 메소드 
	public Document apiModuleXML(String subwaynums) {

		// <?xml version="1.0" encoding="UTF-8"?>
		// <generator>
		// <package>
		// <package-name>com.ysci.theme.aaa</package-name>
		// </package>
		// </generator>
		ArrayList<Subway> subwayList = new ArrayList<>();
		subwayList = dao.selectSubwayArray(subwaynums);
		
		int getAllHuman = 0; // 모든 인원
		String getSubwaynum = "";
		System.out.println(subwayList.toString());
		for (int i = 0; i < subwayList.size(); i++) {// 총인원 구하는 부분
			getAllHuman += Integer.parseInt(subwayList.get(i).getHumanNum());
			getSubwaynum = subwayList.get(0).getSubwayNum();
		}

		Document doc = new Document();
		
		Element root = new Element("SubwayHumanAndSeatService"); // 1

		Element subwaynum = new Element("SUBWAYNUM"); // 2
		Element allHuman = new Element("ALLHUMAN"); // 2 String
		
		Element result = new Element("RESULT"); // 2
		Element code = new Element("CODE"); // 3
		Element message = new Element("MASSAGE"); // 3


		root.addContent(result);// 최초 구문 RESULT row 넣어주기 자동으로 닫아짐1
		
		result.addContent(code); // RESULT 2
		result.addContent(message); // MESSAGE 2
		
		
		if(subwayList.isEmpty()){
			code.setText("INFO-500");
			message.setText("없는 열차입니다.");
			
			
			doc.setRootElement(root); // 마지막에 더해주기
			
			try {
				FileOutputStream out = new FileOutputStream("test.xml");
				// xml 파일을 떨구기 위한 경로와 파일 이름 지정해 주기
				XMLOutputter serializer = new XMLOutputter();

				Format f = serializer.getFormat();
				f.setEncoding("UTF-8");
				// encoding 타입을 UTF-8 로 설정
				f.setIndent(" ");
				f.setLineSeparator("\r\n");
				f.setTextMode(Format.TextMode.TRIM);
				serializer.setFormat(f);

				serializer.output(doc, out);
				out.flush();
				out.close();

				serializer.output(doc, new FileWriter("test.xml"));

				return doc;
			} catch (IOException e) {
				System.err.println(e);
			}
		}
		
		root.addContent(subwaynum); // 1
		root.addContent(allHuman); // 1

		subwaynum.setText(getSubwaynum); // 2002 열차
		allHuman.setText(getAllHuman + ""); // 모든사람
		code.setText("INFO-000");
		String lovelive = "정상 처리되었습니다.";
		message.setText(lovelive);

		for (int i = 0; i < subwayList.size(); i++) {// 총인원 구하는 부분
			// String index = "row"; // 1234..
			String index = Integer.toString(i + 1);
			Element row = new Element("row"); // 1
			root.addContent(row);
			Element subwaynumber = new Element("SUBWAYNUM"); // 2
			row.addContent(subwaynumber);
			Element humannum = new Element("HUMANNUM"); // 2
			row.addContent(humannum);
			Element carnum = new Element("CARNUM"); // 2
			row.addContent(carnum);

			double percent = Math.round(Double.parseDouble(subwayList.get(i).getHumanNum())/160*100);
			Element humanPercent = new Element("HUMANPERCENT"); // 2
			row.addContent(humanPercent);
			
			Element elderlyseat1 = new Element("ELDERLYSEAT1");
			Element elderlyseat2 = new Element("ELDERLYSEAT2");
			Element elderlyseat3 = new Element("ELDERLYSEAT3");
			Element elderlyseat4 = new Element("ELDERLYSEAT4");
			Element elderlyseat5 = new Element("ELDERLYSEAT5");
			Element elderlyseat6 = new Element("ELDERLYSEAT6");
			Element elderlyseat7 = new Element("ELDERLYSEAT7");
			Element elderlyseat8 = new Element("ELDERLYSEAT8");
			Element elderlyseat9 = new Element("ELDERLYSEAT9");
			Element elderlyseat10 = new Element("ELDERLYSEAT10");
			Element elderlyseat11 = new Element("ELDERLYSEAT11");
			Element elderlyseat12 = new Element("ELDERLYSEAT12");
			
			row.addContent(elderlyseat1);
			row.addContent(elderlyseat2);
			row.addContent(elderlyseat3);
			row.addContent(elderlyseat4);
			row.addContent(elderlyseat5);
			row.addContent(elderlyseat6);
			row.addContent(elderlyseat7);
			row.addContent(elderlyseat8);
			row.addContent(elderlyseat9);
			row.addContent(elderlyseat10);
			row.addContent(elderlyseat11);
			row.addContent(elderlyseat12);
			
			subwaynumber.setText(subwaynums);
			carnum.setText(index);
			humannum.setText(subwayList.get(i).getHumanNum());
			humanPercent.setText(Integer.parseInt(String.valueOf(Math.round(percent)))+"");
			elderlyseat1.setText(subwayList.get(i).getElderlySeat1());
			elderlyseat2.setText(subwayList.get(i).getElderlySeat2());
			elderlyseat3.setText(subwayList.get(i).getElderlySeat3());
			elderlyseat4.setText(subwayList.get(i).getElderlySeat4());
			elderlyseat5.setText(subwayList.get(i).getElderlySeat5());
			elderlyseat6.setText(subwayList.get(i).getElderlySeat6());
			elderlyseat7.setText(subwayList.get(i).getElderlySeat7());
			elderlyseat8.setText(subwayList.get(i).getElderlySeat8());
			elderlyseat9.setText(subwayList.get(i).getElderlySeat9());
			elderlyseat10.setText(subwayList.get(i).getElderlySeat10());
			elderlyseat11.setText(subwayList.get(i).getElderlySeat11());
			elderlyseat12.setText(subwayList.get(i).getElderlySeat12());
		}

		doc.setRootElement(root); // 마지막에 더해주기

		try {
			FileOutputStream out = new FileOutputStream("test.xml");
			// xml 파일을 떨구기 위한 경로와 파일 이름 지정해 주기
			XMLOutputter serializer = new XMLOutputter();

			Format f = serializer.getFormat();
			f.setEncoding("UTF-8");
			// encoding 타입을 UTF-8 로 설정
			f.setIndent(" ");
			f.setLineSeparator("\r\n");
			f.setTextMode(Format.TextMode.TRIM);
			serializer.setFormat(f);

			serializer.output(doc, out);
			out.flush();
			out.close();

			serializer.output(doc, new FileWriter("test.xml"));

			return doc;
		} catch (IOException e) {
			System.err.println(e);
		}
		return null;

	}

	// XML을 String으로 출력하는 메소드 
	public String XMLToString(Document doc) {
		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat().setEncoding("UTF-8"));
		return outputter.outputString(doc);
	}

}
