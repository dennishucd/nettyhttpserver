/*
 * Copyright 2016 Dennis Hu
 *
 * Dennis Hu licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package cn.dennishucd.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class CTools {
	private static SimpleDateFormat format = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	static {
		format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
	}
	
	public static String dateToString(Date date) {

		return format.format(date);
	}
	
	public static Date stringToDate(String dateStr) throws ParseException {

		return format.parse(dateStr);
	}

	public static String dateToString(long time) {

		return format.format(time);
	}

	public static String Object2JsonStr(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(format);
		String json = "";

		try {
			json = mapper.writeValueAsString(object);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return json;
	}

	public static <T> String List2JSONStr(List<T> list) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(format);
		StringWriter sw = new StringWriter();

		try {
			mapper.writeValue(sw, list);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return sw.toString();
	}

	public static String getMd5Digest(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(input.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			String hashtext = number.toString(16);
			// Now we need to zero pad it if you actually want the full 32
			// chars.
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T JSONStr2Object(String jsonStr, Class<T> beanCalss) {
		ObjectMapper mapper = new ObjectMapper();
		T bean = null;
		try {
			bean = (T) mapper.readValue(jsonStr, beanCalss);
		} catch (JsonParseException e) {

			e.printStackTrace();
		} catch (JsonMappingException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return bean;

	}

	public static <T> List<T> JSONStr2List(String jsonStr, Class<T> beanCalss) {
		ObjectMapper mapper = new ObjectMapper();
		List<T> beanList = null;
		try {
			// beanList = mapper.readValue(jsonStr, new TypeReference<List<T>>()
			// { });
			TypeFactory t = TypeFactory.defaultInstance();
			// 指定容器结构和类型（这里是ArrayList和clazz）
			beanList = mapper.readValue(jsonStr,
					t.constructCollectionType(ArrayList.class, beanCalss));
		} catch (JsonParseException e) {

			e.printStackTrace();
		} catch (JsonMappingException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return beanList;

	}

	public static String generateRegCode(String packageName, int customerId,
			String dateTime) {
		StringBuilder sb = new StringBuilder();
		sb.append(packageName);
		sb.append("," + customerId);
		sb.append("," + dateTime);

		return getMd5Digest(sb.toString()).toUpperCase();
	}
}
