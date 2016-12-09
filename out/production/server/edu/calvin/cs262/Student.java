package edu.calvin.cs262;

import java.sql.Date;
import java.sql.Blob;

/**
 * A User class for the Student relation
 *
 * @author Loganvp, meliornox
 * @version 12/3/16
 */
class Student {

    private String CalvinID, password, first, last, username, classYear, homeCity, homeState, homeCountry, major,
            majorDepartment, majorNumber, gender, genderWant, religion, mbti, job, hangout, diningPreference, sports,
            studySpot, chapelDay, nationality, vocation, aboutMe, status;
    private char calvinT, calvinU, calvinL, calvinI, calvinP, loft;
    private int hateHope, bQuiv, bunHate, height;
    private boolean hasJob;
    private Date birthday;
    private Blob picture;


    @SuppressWarnings("unused")
    Student() { /* a default constructor, required by Gson */ }

    Student(String CalvinID,
            String password,
            Blob picture,
            String first,
            String last,
            String username,
            String classYear,
            Date birthday,
            String homeCity,
            String homeState,
            String homeCountry,
            String major,
            String majorDepartment,
            String majorNumber,
            String gender,
            String genderWant
            String religion,
            String mbti,
            boolean hasJob,
            String job,
            char calvinT,
            char calvinU,
            char calvinL,
            char calvinI,
            char calvinP,
            String hangout,
            int hateHope,
            String bQuiv,
            String diningPreference,
            String sports,
            int bunHate,
            String studySpot,
            String chapelDay,
            char loft,
            int height,
            String nationality
            String vocation,
            String aboutMe,
            String status) {

        this.CalvinID = CalvinID;
        this.password = password;
        this.picture = picture;
        this.first = first;
        this.last = last;
        this.username = username;
        this.classYear = classYear;
        this.birthday = birthday;
        this.homeCity = homeCity;
        this.homeState = homeState;
        this.homeCountry = homeCountry;
        this.major = major;
        this.majorDepartment = majorDepartment;
        this.majorNumber = majorNumber;
        this.gender = gender;
        this.genderWant = genderWant;
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
        this.studySpot = studySpot;
        this.chapelDay = chapelDay;
        this.loft = loft;
        this.height = height;
        this.nationality = nationality;
        this.vocation = vocation;
        this.aboutMe = aboutMe;
        this.status = status;
    }
/*
 *  GET methods
 */
    @SuppressWarnings("unused")
    public String getCalvinID() { return CalvinID; }

    @SuppressWarnings("unused")
    public String getPassword() { return password; }

    @SuppressWarnings("unused")
    public Blob getBlob() { return picture; }

    @SuppressWarnings("unused")
    public String getFirst() { return first; }

    @SuppressWarnings("unused")
    public String getLast() { return last; }

    @SuppressWarnings("unused")
    public String getUsername() { return username; }

    @SuppressWarnings("unused")
    public String getClassYear() { return classYearYear; }

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
    public String getMajorDepartment() { return majorDepartment; }

    @SuppressWarnings("unused")
    public String getMajorNumber() { return majorNumber; }

    @SuppressWarnings("unused")
    public String getGender() { return gender; }

    @SuppressWarnings("unused")
    public String getGenderWant() { return genderWant; }

    @SuppressWarnings("unused")
    public String getReligion() { return religion; }

    @SuppressWarnings("unused")
    public String getMbti() { return mbti; }

    @SuppressWarnings("unused")
    public boolean getHasJob() { return hasJob; }

    @SuppressWarnings("unused")
    public String getJob() { return job; }

    @SuppressWarnings("unused")
    public char getCalvinT() { return calvinT; }

    @SuppressWarnings("unused")
    public char getCalvinU() { return calvinU; }

    @SuppressWarnings("unused")
    public char getCalvinL() { return calvinL; }

    @SuppressWarnings("unused")
    public char getCalvinI() { return calvinI; }

    @SuppressWarnings("unused")
    public char getCalvinP() { return calvinP; }

    @SuppressWarnings("unused")
    public String getHangout() { return hangout; }

    @SuppressWarnings("unused")
    public int getHateHope() { return hateHope; }

    @SuppressWarnings("unused")
    public String getBQuiv() { return bQuiv; }

    @SuppressWarnings("unused")
    public String getDiningPreference() { return diningPreference; }

    @SuppressWarnings("unused")
    public String getSports() { return sports; }

    @SuppressWarnings("unused")
    public int getBunHate() { return bunHate; }

    @SuppressWarnings("unused")
    public String getStudySpot() { return studySpot; }

    @SuppressWarnings("unused")
    public String getChapelDays() { return chapelDay; }

    @SuppressWarnings("unused")
    public char getLoft() { return loft; }

    @SuppressWarnings("unused")
    public int getHeight() { return height; }

    @SuppressWarnings("unused")
    public String getNationality() { return nationality; }

    @SuppressWarnings("unused")
    public String getVocation() { return vocation; }

    @SuppressWarnings("unused")
    public String getAboutMe() { return aboutMe; }

    @SuppressWarnings("unused")
    public String getStatus() { return status; }

/*
 * SET Methods
 */

    @SuppressWarnings("unused")
    public void setCalvinID(String CalvinID) { this.CalvinID = CalvinID; }

    @SuppressWarnings("unused")
    public void setPassword(String password) { this.password = password; }

    @SuppressWarnings("unused")
    public void setBlob(Blob picture) { this.picture = picture; }

    @SuppressWarnings("unused")
    public void setFirst(String first) { this.first = first; }

    @SuppressWarnings("unused")
    public void setLast(String last) { this.last = last; }

    @SuppressWarnings("unused")
    public void setUsername(String username) { this.username = username; }

    @SuppressWarnings("unused")
    public void setClassYear(String classYear) { this.classYear = classYear; }

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
    public void setMajorDepartment(String majorDepartment) { this.majorDepartment = majorDepartment; }

    @SuppressWarnings("unused")
    public void setMajorNumber(String majorNumber) { this.majorNumber = majorNumber; }

    @SuppressWarnings("unused")
    public void setGender(String gender) { this.gender = gender; }

    @SuppressWarnings("unused")
    public void setGenderWant(String genderWant) { this.genderWant = genderWant; }

    @SuppressWarnings("unused")
    public void setReligion(String religion ) { this.religion = religion; }

    @SuppressWarnings("unused")
    public void setMbti(String mbti) { this.mbti = mbti; }

    @SuppressWarnings("unused")
    public void setHasJob(boolean hasJob) { this.hasJob = hasJob; }

    @SuppressWarnings("unused")
    public void setJob(String job) { this.job = job; }

    @SuppressWarnings("unused")
    public void setCalvinT(char calvinT) { this.calvinT = calvinT; }

    @SuppressWarnings("unused")
    public void setCalvinU(char calvinU) { this.calvinU = calvinU; }

    @SuppressWarnings("unused")
    public void setCalvinL(char calvinL) { this.calvinL = calvinL; }

    @SuppressWarnings("unused")
    public void setCalvinI(char calvinI) { this.calvinI = calvinI; }

    @SuppressWarnings("unused")
    public void setCalvinP(char calvinP) { this.calvinP = calvinP; }

    @SuppressWarnings("unused")
    public void setHangout(String hangout) { this.hangout = hangout; }

    @SuppressWarnings("unused")
    public void setHateHope(int hateHope) { this.hateHope = hateHope; }

    @SuppressWarnings("unused")
    public void setBQuiv(String bQuiv) { this.bQuiv = bQuiv; }

    @SuppressWarnings("unused")
    public void setDiningPreference(String diningPreference) { this.diningPreference = diningPreference; }

    @SuppressWarnings("unused")
    public void setSports(String sports) { this.sports = sports; }

    @SuppressWarnings("unused")
    public void setBunHate(int bunHate) { this.bunHate = bunHate; }

    @SuppressWarnings("unused")
    public void setStudySpot(String studySpot) { this.studySpot = studySpot; }

    @SuppressWarnings("unused")
    public void setChapelDays(String chapelDay) { this.chapelDay = chapelDay; }

    @SuppressWarnings("unused")
    public void setLoft(char loft) { this.loft = loft; }

    @SuppressWarnings("unused")
    public void setHeight(int height) { this.height = height; }

    @SuppressWarnings("unused")
    public void setNationality(String nationality) { this.nationality = nationality; }

    @SuppressWarnings("unused")
    public void setVocation(String vocation) { this.vocation = vocation; }

    @SuppressWarnings("unused")
    public void setAboutMe(String aboutMe) { this.aboutMe = aboutMe; }

    @SuppressWarnings("unused")
    public void setStatus(String status) { this.status = status; }
}
