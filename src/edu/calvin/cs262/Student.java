package edu.calvin.cs262;

import java.sql.Date;

/**
 * A User class for the Student relation
 *
 * @author Loganvp
 * @version 12/3/16
 */
class Student {

    private String CalvinID, password, first, last, username, calvinGradMonth, homeCity, homeState, homeCountry, major,
        gender, religion, mbti, job, hangout, diningPreference, sports, vocation, studySpot, chapelDay, nationality;
    private int collegeStartYear, calvinGradYear, hateHope, bQuiv, bunHate, oceanO, oceanC, oceanE, oceanA, oceanN, height;
    private boolean hasJob, calvinT, calvinU, calvinL, calvinI, calvinP, loft;
    private Date birthday;


            @SuppressWarnings("unused")
    Student() { /* a default constructor, required by Gson */ }

    Student(String CalvinID, String password, String first, String last, String username, int collegeStartYear, int calvinGradYear,
            String calvinGradMonth, Date birthday, String homeCity, String homeState, String homeCountry, String major,
            String gender, String religion, String mbti, boolean hasJob, String job, boolean calvinT, boolean calvinU, boolean calvinL,
            boolean calvinI, boolean calvinP, Sting hangout, int hateHope, int bQuiv, String diningPreference, String sports,
            Int bunHate, String vocation, String studySpot, String chapelDay, boolean loft, int oceanO, int oceanC, int oceanE,
            int oceanA, int oceanN, int height, String nationality) {

        this.CalvinID = CalvinID;
        this.password = password;
        this.first = first;
        this.last = last;
        this.username = username;
        this.collegeStartYear = collegeStartYear;
        this.calvinGradYear = calvinGradYear;
        this.calvinGradMonth = calvinGradMonth;
        this.birthday = birthday;
        this.homeCity = homeCity;
        this.homeState = homeState;
        this.homeCountry = homeCountry;
        this.major = major;
        this.gender = gender;
        this.religion = religion;
        this.mbti = mbti;
        this.hasJob = hasJob;
        this.job = job;
        this.calvinT = calvinT;
        this.calvinU = calvinU;
        this.calvinL = calvinL;
        this.calvinI = calvinI;
        this.calvinP = calvinP;
        this.hangout = hangout;
        this.hateHope = hateHope;
        this.bQuiv = bQuiv;
        this.diningPreference = diningPreference;
        this.sports = sports;
        this.bunHate = bunHate;
        this.vocation = vocation;
        this.studySpot = studySpot;
        this.chapelDay = chapelDay;
        this.loft = loft;
        this.oceanO = oceanO;
        this.oceanC = oceanC;
        this.oceanE = oceanE;
        this.oceanA = oceanA;
        this.oceanN = oceanN;
        this.height = height;
        this.nationality = nationality;
    }
/*
 *  GET methods
 */
    @SuppressWarnings("unused")
    public String getCalvinID() { return CalvinID; }

    @SuppressWarnings("unused")
    public String getPassword() { return password; }

    @SuppressWarnings("unused")
    public String getFirst() { return first; }

    @SuppressWarnings("unused")
    public String getLast() { return last; }

    @SuppressWarnings("unused")
    public String getUsername() { return username; }

    @SuppressWarnings("unused")
    public int getCollegeStartYear() { return collegeStartYear; }

    @SuppressWarnings("unused")
    public int getCalvinGradYear() { return calvinGradYear; }

    @SuppressWarnings("unused")
    public String getCalvinGradMonth() { return calvinGradMonth; }

    @SuppressWarnings("unused")
    public Date getBirthday() { return birthday; }

    @SuppressWarnings("unused")
    public String getHomeCity() { return homeCity; }

    @SuppressWarnings("unused")
    public String getHomeState() { return homeState; }

    @SuppressWarnings("unused")
    public String getHomeCountry() { return homeCountry; }

    @SuppressWarnings("unused")
    public String getMajor() { return major; }

    @SuppressWarnings("unused")
    public String getGender() { return gender; }

    @SuppressWarnings("unused")
    public String getReligion() { return religion; }

    @SuppressWarnings("unused")
    public String getMbti() { return mbti; }

    @SuppressWarnings("unused")
    public boolean getHasJob() { return hasjob; }

    @SuppressWarnings("unused")
    public String getJob() { return job; }

    @SuppressWarnings("unused")
    public boolean getCalvinT() { return calvinT; }

    @SuppressWarnings("unused")
    public boolean getCalvinU() { return calvinU; }

    @SuppressWarnings("unused")
    public boolean getCalvinL() { return calvinL; }

    @SuppressWarnings("unused")
    public boolean getCalvinI() { return calvinI; }

    @SuppressWarnings("unused")
    public boolean getCalvinP() { return calvinP; }

    @SuppressWarnings("unused")
    public String getHangout() { return hangout; }

    @SuppressWarnings("unused")
    public int getHateHope() { return hateHope; }

    @SuppressWarnings("unused")
    public int getBQuiv() { return bQuiv; }

    @SuppressWarnings("unused")
    public String getDiningPreference() { return diningPreference; }

    @SuppressWarnings("unused")
    public String getSports() { return sports; }

    @SuppressWarnings("unused")
    public int getBunHate() { return bunHate; }

    @SuppressWarnings("unused")
    public String getVocation() { return vocation; }

    @SuppressWarnings("unused")
    public String getStudySpot() { return studySpot; }

    @SuppressWarnings("unused")
    public String getChapelDays() { return chapelDay; }

    @SuppressWarnings("unused")
    public boolean getLoft() { return loft; }

    @SuppressWarnings("unused")
    public int getOceanO() { return oceanO; }

    @SuppressWarnings("unused")
    public int getOceanC() { return oceanC; }

    @SuppressWarnings("unused")
    public int getOceanE() { return oceanE; }

    @SuppressWarnings("unused")
    public int getOceanA() { return oceanA; }

    @SuppressWarnings("unused")
    public int getOceanN() { return oceanN; }

    @SuppressWarnings("unused")
    public int getHeight() { return height; }

    @SuppressWarnings("unused")
    public String getNationality() { return nationality; }

/*
 * SET Methods
 */

    @SuppressWarnings("unused")
    public void setCalvinID(String CalvinID) { this.CalvinID = CalvinID; }

    @SuppressWarnings("unused")
    public void setPassword(String password) { this.password = password; }

    @SuppressWarnings("unused")
    public void setFirst(String first) { this.first = first; }

    @SuppressWarnings("unused")
    public void setLast(String last) { this.last = last; }

    @SuppressWarnings("unused")
    public void setUsername(String username) { this.username = username; }

    @SuppressWarnings("unused")
    public void setCollegeStartYear(int collegeStartYear) { this.collegeStartYear = collegeStartYear; }

    @SuppressWarnings("unused")
    public void setCalvinGradYear(int calvinGradYear) { this.calvinGradYear = calvinGradYear; }

    @SuppressWarnings("unused")
    public void setCalvinGradMonth(String calvinGradMonth) { this.calvinGradMonth = calvinGradMonth; }

    @SuppressWarnings("unused")
    public void setBirthday(Date birthday) { this.birthday = birthday; }

    @SuppressWarnings("unused")
    public void setHomeCity(String homeCity) { this.homeCity = homeCity; }

    @SuppressWarnings("unused")
    public void setHomeState(String homeState) { this.homeState = homeState; }

    @SuppressWarnings("unused")
    public void setHomeCountry(String homeCountry) { this.homeCountry = homeCountry; }

    @SuppressWarnings("unused")
    public void setMajor(String major) { this.major = major; }

    @SuppressWarnings("unused")
    public void setGender(String gender) { this.gender = gender; }

    @SuppressWarnings("unused")
    public void setReligion(String religion ) { this.religion = religion; }

    @SuppressWarnings("unused")
    public void setMbti(String mbti) { this.mbti = mbti; }

    @SuppressWarnings("unused")
    public void setHasJob(boolean hasJob) { this.hasJob = hasJob; }

    @SuppressWarnings("unused")
    public void setJob(String job) { this.job = job; }

    @SuppressWarnings("unused")
    public void setCalvinT(boolean calvinT) { this.calvinT = calvinT; }

    @SuppressWarnings("unused")
    public void setCalvinU(boolean calvinU) { this.calvinU = calvinU; }

    @SuppressWarnings("unused")
    public void setCalvinL(boolean calvinL) { this.calvinL = calvinL; }

    @SuppressWarnings("unused")
    public void setCalvinI(boolean calvinI) { this.calvinI = calvinI; }

    @SuppressWarnings("unused")
    public void setCalvinP(boolean calvinP) { this.calvinP = calvinP; }

    @SuppressWarnings("unused")
    public void setHangout(String hangout) { this.hangout = hangout; }

    @SuppressWarnings("unused")
    public void setHateHope(int hateHope) { this.hateHope = hateHope; }

    @SuppressWarnings("unused")
    public void setBQuiv(int bQuiv) { this.bQuiv = bQuiv; }

    @SuppressWarnings("unused")
    public void setDiningPreference(String diningPreference) { this.diningPreference = diningPreference; }

    @SuppressWarnings("unused")
    public void setSports(String sports) { this.sports = sports; }

    @SuppressWarnings("unused")
    public void setBunHate(int bunHate) { this.bunHate = bunHate; }

    @SuppressWarnings("unused")
    public void setVocation(String vocation) { this.vocation = vocation; }

    @SuppressWarnings("unused")
    public void setStudySpot(String studySpot) { this.studySpot = studySpot; }

    @SuppressWarnings("unused")
    public void setChapelDays(String chapelDay) { this.chapelDay = chapelDay; }

    @SuppressWarnings("unused")
    public void setLoft(boolean loft) { this.loft = loft; }

    @SuppressWarnings("unused")
    public void setOceanO(int oceanO) { this.oceanO = oceanO; }

    @SuppressWarnings("unused")
    public void setOceanC(int oceanC) { this.oceanC = oceanC; }

    @SuppressWarnings("unused")
    public void setOceanE(int oceanE) { this.oceanE = oceanE; }

    @SuppressWarnings("unused")
    public void setOceanA(int oceanA) { this.oceanA = oceanA; }

    @SuppressWarnings("unused")
    public void setOceanN(int oceanN) { this.oceanN = oceanN; }

    @SuppressWarnings("unused")
    public void setHeight(int height) { this.height = height; }

    @SuppressWarnings("unused")
    public void setNationality(String nationality) { this.nationality = nationality; }

}
