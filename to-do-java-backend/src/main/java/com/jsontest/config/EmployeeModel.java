package com.jsontest.config;


public class EmployeeModel {
	
	
		 
	    // Class data members
		private String employeename;
		private Integer registraion_id;
	      // Constructor
	    public EmployeeModel() {}
	    
	    public String getEmployeename() {
			return employeename;
		}

		public void setEmployeename(String employeename) {
			this.employeename = employeename;
		}

		public Integer getRegistraion_id() {
			return registraion_id;
		}

	public void setRegistraion_id(Integer registraion_id) {
			this.registraion_id = registraion_id;
		}

	@Override
	public String toString() {
		return "EmployeeModel [employeename=" + employeename + ", registraion_id=" + registraion_id + "]";
	}


	
	    
	 
	   
	}
	

