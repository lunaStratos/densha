package ikuzo.kimi.densha.util;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import ikuzo.kimi.densha.dao.SubwayDAO;
import ikuzo.kimi.densha.vo.Subway;

public class apiMake {
	
	
	@Autowired
	SubwayDAO dao;
	
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

		root.addContent(subwaynum); // 1
		root.addContent(allHuman); // 1

		root.addContent(result);// 최초 구문 RESULT row 넣어주기 자동으로 닫아짐1

		result.addContent(code); // RESULT 2
		result.addContent(message); // MESSAGE 2

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
			Element humannum = new Element("HUMANNUM"); // 2
			row.addContent(humannum);
			Element carnum = new Element("CARNUM"); // 2
			row.addContent(carnum);

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
			
			carnum.setText(index);
			humannum.setText(subwayList.get(i).getHumanNum());
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

	public String XMLToString(Document doc) {
		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat().setEncoding("UTF-8"));
		return outputter.outputString(doc);
	}

}
