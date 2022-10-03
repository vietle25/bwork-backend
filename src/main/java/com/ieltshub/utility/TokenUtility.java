package com.ieltshub.utility;

import java.util.Calendar;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * Gen token util
 * 
 * @author tuanlt
 * @date Jul 14, 2016
 * @since 1.0
 */
public class TokenUtility {
	public static String key = "joco"; // 128 bit key
//	private static int expirationSecond = 43200; // time out token = config

	/**
	 * Gen token
	 * @param identifier
	 * @return
	 */
	public static String genToken(String identifier) {
//		Calendar cal = Calendar.getInstance(); // creates calendar
//		cal.setTime(new Date()); // sets calendar time/date
//		cal.add(Calendar.SECOND, expirationSecond); 
//		cal.add(Calendar.HOUR_OF_DAY, expirationHour); 
//		cal.getTime(); // returns new date object, one hour in the future
//		Claims claims = Jwts.claims().setSubject(identifier).setExpiration(cal.getTime());
		Claims claims = Jwts.claims().setSubject(identifier);
		String token = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, key).compact();
		return token;
	}

	/**
	 * Check token valid
	 * @param token
	 * @return
	 */
	public static boolean checkToken(String token, String userName) {
		try {
			return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject().equals(userName);
//			Jwts.parser().setSigningKey(key).parseClaimsJws(token);
		} catch (ExpiredJwtException e) {
			//time out token
			return false;
		} catch (MalformedJwtException e) {
			return false;
		} catch (UnsupportedJwtException e) {
			return false;
		} catch (SignatureException e) {
			return false;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}
}
