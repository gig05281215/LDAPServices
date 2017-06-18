package com.trimuph.ldap;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import org.apache.directory.api.ldap.model.cursor.EntryCursor;
import org.apache.directory.api.ldap.model.entry.DefaultEntry;
import org.apache.directory.api.ldap.model.entry.DefaultModification;
import org.apache.directory.api.ldap.model.entry.Entry;
import org.apache.directory.api.ldap.model.entry.Modification;
import org.apache.directory.api.ldap.model.entry.ModificationOperation;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.api.ldap.model.exception.LdapInvalidDnException;
import org.apache.directory.api.ldap.model.message.DeleteResponse;
import org.apache.directory.api.ldap.model.message.ResultCodeEnum;
import org.apache.directory.api.ldap.model.message.SearchScope;
import org.apache.directory.api.ldap.model.name.Dn;
import org.apache.directory.api.ldap.model.schema.SchemaManager;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.apache.directory.ldap.client.api.LdapNetworkConnection;
import org.apache.directory.server.core.api.DirectoryService;
import org.apache.directory.server.core.api.DnFactory;
import org.apache.directory.server.core.api.interceptor.context.AddOperationContext;
import org.apache.directory.server.core.partition.impl.btree.jdbm.JdbmIndex;
import org.apache.directory.server.core.partition.impl.btree.jdbm.JdbmPartition;
import org.apache.directory.server.core.shared.DefaultDnFactory;

public class LDAPOperationHandler {
	LdapConnection connection;
	public void connect(){
		try {
			connection = new LdapNetworkConnection( "localhost",10389 );
			connection.bind( "uid=admin,ou=system", "secret" );
			
		} catch (LdapException e) {
			e.printStackTrace();
		}
	}// connect
	
	/**
	 * 只能在目录服务嵌入式发布时进行分区创建
	 */
	/*****
	public void makaPatitions(){
		try {
		// Get the SchemaManager, we need it for this addition
		SchemaManager schemaManager = connection.getSchemaManager();
		CacheManager manager = CacheManager.create("ehcache.xml");
		
		Cache cache = manager.getCache("sampleCache1");
		// Create the partition
		DnFactory df = new DefaultDnFactory(schemaManager,cache);
		JdbmPartition sevenseasPartition = new JdbmPartition( schemaManager,df );
		sevenseasPartition.setId("sevenseas");
		
		Dn suffixDn = new Dn( schemaManager, "ou=sevenseas" );
		
		sevenseasPartition.setSuffixDn( suffixDn );
		sevenseasPartition.setCacheSize(1000);
		
//		sevenseasPartition.init(connection);
		
		sevenseasPartition.setPartitionPath( new URI("file://E:/ApacheDS/sevenOean"));
		
		// Create some indices (optional)
		sevenseasPartition.addIndex( new JdbmIndex( "objectClass", false ) );
		sevenseasPartition.addIndex( new JdbmIndex( "o", false ) );

		// Initialize the partition
		sevenseasPartition.initialize();

		// create the context entry
		Entry contextEntry = new DefaultEntry( schemaManager, "o=sevenseas",
		    "objectClass: top", 
		    "objectClass: organization",
		    "o: sevenseas" );

		// add the context entry
		sevenseasPartition.add( new AddOperationContext( null, contextEntry ) );
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (LdapInvalidDnException e) {
			e.printStackTrace();
		} catch (LdapException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	***/
	
	/**
	 * 增加entry
	 */
	public void addEntry(String entryName){
		  try {
			connection.add(
				        new DefaultEntry(
				        		 "o="+entryName+",ou=system", // The Dn
				        		 "ObjectClass: organization",
				                 "ObjectClass: top"
				        			) );
			assert(true);
		} catch (LdapException e) {
			e.printStackTrace();
			assert(false);
		}
			  //  assertTrue( connection.exists( "cn=testadd,ou=system" ) );
	}// addEntry
	
	

	/**
	 * 删除entry
	 */
	public void deleteEntry(String entryName){
		try{
			connection.delete(  "o="+entryName+",ou=system" );
		}
		catch(Exception e){
			
		}
	}// deleteEntry
	
	
	/**
	 * 增加一个attribute
	 * @param entryName
	 */
	public void modifyEntry(String entryName){
		Modification addedGivenName = new DefaultModification( ModificationOperation.ADD_ATTRIBUTE, "street",
			    "yyy" );
		try {
			connection.modify( "o="+entryName+",ou=system" ,addedGivenName);
			assert(true);
		} catch (LdapException e) {
			e.printStackTrace();
			assert(false);
		}
	}
	
	/**
	 * 检索
	 */
	public void search(){
		EntryCursor cursor;
		try {
			cursor = connection.search( "ou=system", "(objectclass=*)", SearchScope.ONELEVEL );
			for ( Entry entry : cursor )
			{
				System.out.println( entry );
			}
			cursor.close();
		} catch (LdapException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * 删除entry
	 */
	public void disconnect(){
		try{
		  connection.unBind();
		  connection.close();
		  assert(true);
		}
		catch(Exception e){
			e.printStackTrace();
			assert(false);
		}
	}// deleteEntry
}
