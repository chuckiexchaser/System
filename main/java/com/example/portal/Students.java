package com.example.portal;

public class Students {

   String studentID;
   String studentName, course, year, level;

   public Students (String studentID, String studentName, String course, String year, String level) {
      this.studentID = studentID;
      this.studentName = studentName;
      this.course = course;
      this.year = year;
      this.level = level;
   }

   public String getStudentID() {
      return studentID;
   }

   public void setStudentID() {
      this.studentID = studentID;
   }

   public void setStudentName() {
      this.studentName = studentName;
   }

   public void setCourse() {
      this.course = course;
   }

   public void setYear() {
      this.year = year;
   }

   public void setLevel() {
      this.level = level;
   }
}
