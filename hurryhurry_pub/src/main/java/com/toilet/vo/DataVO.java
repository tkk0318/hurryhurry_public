package com.toilet.vo;

public class DataVO {
	private String 
				TOILET_NAME,
				ADDRESS,
				ADDRESS2,
				HOURS,
				GENDER,
				PHONE,
				STATUS,
				DIAPER_CHANGED,
				mapx,
				mapy;

	private int IMPAIRED_M_B,
			IMPAIRED_M_S,	
			IMPAIRED_W_B,
			CHILD_M_B,
			CHILD_M_S,
			CHILD_W_B,
			
			IMPAIRED,
			CHILD;
	



	public DataVO(String tOILET_NAME, String aDDRESS, String aDDRESS2, String hOURS, String gENDER, String pHONE, String dIAPER_CHANGED, String mapx, String mapy, int iMPAIRED_M_B, int iMPAIRED_M_S,
			int iMPAIRED_W_B, int cHILD_M_B, int cHILD_M_S, int cHILD_W_B) {
		super();
		TOILET_NAME = tOILET_NAME;
		ADDRESS = aDDRESS;
		ADDRESS2 = aDDRESS2;
		HOURS = hOURS;
		GENDER = gENDER;
		PHONE = pHONE;
		DIAPER_CHANGED = dIAPER_CHANGED;
		this.mapx = mapx;
		this.mapy = mapy;
		IMPAIRED_M_B = iMPAIRED_M_B;
		IMPAIRED_M_S = iMPAIRED_M_S;
		IMPAIRED_W_B = iMPAIRED_W_B;
		CHILD_M_B = cHILD_M_B;
		CHILD_M_S = cHILD_M_S;
		CHILD_W_B = cHILD_W_B;

		CHILD = CHILD_M_B+CHILD_M_S+CHILD_W_B;
		IMPAIRED = IMPAIRED_M_B+IMPAIRED_M_S+IMPAIRED_W_B;
		
	}
	public DataVO(String tOILET_NAME, String aDDRESS, String aDDRESS2, String hOURS, String gENDER, String pHONE, String dIAPER_CHANGED, String mapx, String mapy) {
		super();
		TOILET_NAME = tOILET_NAME;
		ADDRESS = aDDRESS;
		ADDRESS2 = aDDRESS2;
		HOURS = hOURS;
		GENDER = gENDER;
		PHONE = pHONE;
		DIAPER_CHANGED = dIAPER_CHANGED;
		this.mapx = mapx;
		this.mapy = mapy;;
		
	}
	
	public DataVO(String mapx, String mapy) {
		this.mapx = mapx;
		this.mapy = mapy;;
		
	}
	

	public String getMapx() {
		return mapx;
	}

	public void setMapx(String mapx) {
		this.mapx = mapx;
	}

	public String getMapy() {
		return mapy;
	}

	public void setMapy(String mapy) {
		this.mapy = mapy;
	}

	
	

	public int getIMPAIRED() {
		return IMPAIRED;
	}

	public void setIMPAIRED(int IMPAIRED_M_B, int IMPAIRED_M_S, int IMPAIRED_W_B ) {
		IMPAIRED = IMPAIRED_M_B+IMPAIRED_M_S+IMPAIRED_W_B;
	}

	public int getCHILD() {
		return CHILD;
	}

	public void setCHILD(int CHILD_M_B, int CHILD_M_S, int CHILD_W_B) {
		CHILD = CHILD_M_B+CHILD_M_S+CHILD_W_B;
	}


	public String getTOILET_NAME() {
		return TOILET_NAME;
	}

	public void setTOILET_NAME(String tOILET_NAME) {
		TOILET_NAME = tOILET_NAME;
	}

	public String getADDRESS() {
		return ADDRESS;
	}

	public void setADDRESS(String aDDRESS) {
		ADDRESS = aDDRESS;
	}

	public String getHOURS() {
		return HOURS;
	}

	public void setHOURS(String hOURS) {
		HOURS = hOURS;
	}

	public String getGENDER() {
		return GENDER;
	}

	public void setGENDER(String gENDER) {
		GENDER = gENDER;
	}

	public int getIMPAIRED_M_B() {
		return IMPAIRED_M_B;
	}

	public void setIMPAIRED_M_B(int iMPAIRED_M_B) {
		IMPAIRED_M_B = iMPAIRED_M_B;
	}

	public int getIMPAIRED_M_S() {
		return IMPAIRED_M_S;
	}

	public void setIMPAIRED_M_S(int iMPAIRED_M_S) {
		IMPAIRED_M_S = iMPAIRED_M_S;
	}

	public int getIMPAIRED_W_B() {
		return IMPAIRED_W_B;
	}

	public void setIMPAIRED_W_B(int iMPAIRED_W_B) {
		IMPAIRED_W_B = iMPAIRED_W_B;
	}

	public int getCHILD_M_B() {
		return CHILD_M_B;
	}

	public void setCHILD_M_B(int cHILD_M_B) {
		CHILD_M_B = cHILD_M_B;
	}

	public int getCHILD_M_S() {
		return CHILD_M_S;
	}

	public void setCHILD_M_S(int cHILD_M_S) {
		CHILD_M_S = cHILD_M_S;
	}

	public int getCHILD_W_B() {
		return CHILD_W_B;
	}

	public void setCHILD_W_B(int cHILD_W_B) {
		CHILD_W_B = cHILD_W_B;
	}

	public String getPHONE() {
		return PHONE;
	}

	public void setPHONE(String pHONE) {
		PHONE = pHONE;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	public String getDIAPER_CHANGED() {
		return DIAPER_CHANGED;
	}

	public void setDIAPER_CHANGED(String dIAPER_CHANGED) {
		DIAPER_CHANGED = dIAPER_CHANGED;
	}
	
	public String getADDRESS2() {
		return ADDRESS2;
	}

	public void setADDRESS2(String aDDRESS2) {
		ADDRESS2 = aDDRESS2;
	}
				
					
}
