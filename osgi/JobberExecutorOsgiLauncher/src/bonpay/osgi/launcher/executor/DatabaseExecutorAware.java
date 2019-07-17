package bonpay.osgi.launcher.executor;

import java.sql.CallableStatement;



import java.sql.Connection;
import java.sql.Types;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import bonpay.osgi.launcher.IModuleExecutorAware;
import bonpay.osgi.service.interf.IExecutor;

import database.IConnectionAware;


public class DatabaseExecutorAware implements IExecutorAware{
	private Logger logger=Logger.getLogger(this.getClass());
	private IConnectionAware connectionAware;
	private IModuleExecutorAware moduleExecutorAware;
	
	public DatabaseExecutorAware(IConnectionAware connectionAware, IModuleExecutorAware moduleExecutorAware){
		this.connectionAware=connectionAware;
		this.moduleExecutorAware=moduleExecutorAware;
	}
	
	/*private void printResultSet(ResultSet rs){
		System.out.println("---------------------");
		SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
		try{
			while(rs.next()){
				System.out.println("id_job:"+rs.getInt("id_ext_job")+"    next_start:"+sdf.format(new Date(rs.getTimestamp("next_start").getTime()))+"   now: "+sdf.format(new Date()));
			}
		}catch(Exception ex){
		}
	}*/
	
	
	@Override
	public IExecutor[] getExecutors() {
		logger.debug("�������� ��� Executor-�");
		ArrayList<IExecutor> returnValue=new ArrayList<IExecutor>();
		Connection connection=this.connectionAware.getConnection();
		// �������� �� ���� ������ �� ������, ������� ����� ����������
		try{
			ArrayList<ResultSetWrap> list=new ArrayList<ResultSetWrap>();
			String query=this.getQuery();
			logger.debug(query);
			ResultSet rs=connection.createStatement().executeQuery(query);
			//printResultSet(rs);
			//rs.close();
			//rs=connection.createStatement().executeQuery(this.getQuery());
			
			while(rs.next()){
				logger.info("������� ��������� Executor - ����������");
				IExecutor executor=this.readExecutorFromResultSet(rs);
				if(executor!=null){
					returnValue.add(executor);
					// INFO ����� ��������� ������� ���������� ������� �������� 
					int period=rs.getInt("period_of_start");
					Date nextStart=null;
					try{
						nextStart=new Date(rs.getTimestamp("NEXT_START").getTime());
					}catch(Exception ex){};
					if(nextStart==null){
						nextStart=new Date();
					}
					SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
					//System.out.println("next_start before: "+sdf.format(nextStart));
					Calendar calendar=Calendar.getInstance();
					calendar.setTime(nextStart);
					calendar.add(Calendar.SECOND, period);
					nextStart=calendar.getTime();
					if(nextStart.before(new Date())){
						calendar.setTime(new Date());
						calendar.add(Calendar.SECOND, period);
						nextStart.setTime(calendar.getTime().getTime());
					}
					logger.info("��������� ����� Executor-� after: "+sdf.format(nextStart));
					list.add(new ResultSetWrap(rs.getInt("id_ext_job"), period, nextStart));
				}else{
					logger.info("Executor does not exists ");
				}
			}
			rs.getStatement().close();
			logger.debug("���������� ��� ������ ���������� ������ ����� �����"); 
			for(int counter=0;counter<list.size();counter++){
				if(this.setAsExecuted(connection, 
									  list.get(counter).getId(), 
									  list.get(counter).getPeriod(), 
									  list.get(counter).getTimeWrite())){
					logger.debug("setAsExecuted OK:"+list.get(counter).getId());
				}else{
					logger.error("setAsExecuted Error:"+list.get(counter).getId());
				};
			}
			if(list.size()==0){
				logger.debug("executor's does not exists ");
			}
		}catch(Exception ex){
			logger.error("getExecutors Exception: "+ex.getMessage(),ex);
		}finally{
			try{
				connection.close();
			}catch(Exception ex){};
		}
		return returnValue.toArray(new IExecutor[]{});
	}
	
	/** �������� executor �� ResultSet */
	private IExecutor readExecutorFromResultSet(ResultSet rs){
		IExecutor returnValue=null;
		String className=null;
		logger.debug("�������� �� ResultSet Executor");
		try{
			className=rs.getString("IEXECUTOR_CLASS_FULL");
			logger.debug("EXECUTOR_CLASS_FULL:"+className);
			returnValue=(IExecutor)this.moduleExecutorAware.getModuleExecutor().getExecutorByName(className);
			if(returnValue==null){
				logger.info("readExecutorFromResultSet: Executor does not found:"+className);
			}else{
				logger.debug("readExecutorFromResultSet: Executor was get: "+returnValue);
			}
		}catch(Exception ex){
			logger.error(" readExecutorFromResultSet "+className+" Exception:"+ex.getMessage(),ex);
		};
		return returnValue;
	}
	
	/** ���������� ��� ����������/������������  */
	private boolean setAsExecuted(Connection connection, int id, int period, Date timeWrite){
		logger.debug("���������� Executor ��� ���������� "+id);
		boolean returnValue=false;
		// Connection connection=this.connectionAware.getConnection();
		try{
			// �������� ��� ������������/���������� 
			// updateConnection.createStatement().executeQuery(this.getUpdateQuery(rs));
			/* 
			 int id=rs.getInt("id_ext_job");
			 int period=rs.getInt("period_of_start");
			Date nextStart=null;
			try{
				nextStart=new Date(rs.getTimestamp("NEXT_START").getTime());
			}catch(Exception ex){};
			if(nextStart==null){
				nextStart=new Date();
			}
			//SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
			//System.out.println("next_start before: "+sdf.format(nextStart));
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(nextStart);
			calendar.add(Calendar.SECOND, period);
			nextStart=calendar.getTime();
			if(nextStart.before(new Date())){
				calendar.setTime(new Date());
				calendar.add(Calendar.SECOND, period);
				nextStart.setTime(calendar.getTime().getTime());
			}
			*/
			
			//System.out.println("next_start after: "+sdf.format(nextStart));
			// INFO �������� ������ ��� ����������, � ��������� ����� ���������� �� ��������� ������ ������ � ������� 
			/* PreparedStatement ps=updateConnection.prepareStatement("update jobber set next_start=? where id=?");
			ps.setTimestamp(1, new Timestamp(nextStart.getTime()));
			ps.setInt(2, id);
			ps.executeUpdate();
			updateConnection.commit();
			*/
			/* PACK_UI_EXT_JOB.update_ext_job(
				     p_id_ext_job IN NUMBER  -- �� ������
				    ,p_next_start IN DATE -- ���� ���������� �������
				    ,p_result_msg OUT VARCHAR2 -- ��������� �� ������
				) RETURN VARCHAR2 -- 0 - ������ ���, ������ - ������
			*/
			String query="{?= call BC_ADMIN.PACK_UI_EXT_JOB.update_ext_job(?,?,?)}";
			logger.debug(query);
			CallableStatement statement = connection.prepareCall(query);
			statement.registerOutParameter(1, Types.VARCHAR); // return value
			statement.setInt(2, id); // �� ������
			statement.setTimestamp(3, new java.sql.Timestamp(timeWrite.getTime()));
			// statement.setDate(3, new java.sql.Date(nextStart.getTime())); // ���� ���������� ������
			statement.registerOutParameter(4, Types.VARCHAR); // ��������� �� ������
            statement.executeUpdate();
			if(statement.getString(1).equals("0")){
				returnValue=true;
				logger.debug("procedure Execute OK ");
			}else{
				logger.error("��������� ��������� � �������� Exception: "+statement.getString(4));
				returnValue=false;
			}
            statement.close();
			connection.commit();
			returnValue=true;
		}catch(Exception ex){
			logger.error("setAsExecuted Exception:"+ex.getMessage(),ex);
		}finally{
			try{
				// ������ ��������� connection, ������ ��� �� ����� ���� ����������� �� ������������ ������ 
				//connection.close();
			}catch(Exception ex){};
		}
		return returnValue;
	}
	
	SimpleDateFormat sdf=new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	
	/** ������ � ���� ������ ��� ��������� ������� */
	private String getQuery(){
		// �������� ��� ������ �� ����, ��� ���� ����� �� ��� �������� ������� ��� �������������� � {@link IExecutor}
		// return "select * from jobber where jobber.next_start<'now'";
		// INFO Query �������� ��� Jobber-�, ������� ����� ��������� 
		// return "select * from bc_admin.vc_ext_jobs_all where next_start<SYSDATE";
		String query="select * from bc_admin.vc_ext_jobs_all where next_start<to_date('"+sdf.format(new Date())+"','dd.mm.rrrr hh24:mi:ss')";
		logger.debug(query);
		return query;
		
	}
}

class ResultSetWrap{
	private int id;
	private int period;
	private Date timeWrite;	
	
	public ResultSetWrap(int id, int period, Date timeWrite){
		this.id=id;
		this.period=period;
		this.timeWrite=timeWrite;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the period
	 */
	public int getPeriod() {
		return period;
	}

	/**
	 * @param period the period to set
	 */
	public void setPeriod(int period) {
		this.period = period;
	}

	/**
	 * @return the timeWrite
	 */
	public Date getTimeWrite() {
		return timeWrite;
	}

	/**
	 * @param timeWrite the timeWrite to set
	 */
	public void setTimeWrite(Date timeWrite) {
		this.timeWrite = timeWrite;
	}
	
}
