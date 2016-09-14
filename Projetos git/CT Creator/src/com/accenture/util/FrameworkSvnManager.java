package com.accenture.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNStatus;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;
import org.tmatesoft.svn.core.wc2.SvnCheckout;
import org.tmatesoft.svn.core.wc2.SvnOperationFactory;
import org.tmatesoft.svn.core.wc2.SvnTarget;

public class FrameworkSvnManager {

	private SVNRepository repo;
	private static SVNClientManager ourClientManager;
	private String myWorkingCopyPath;
	private SVNURL repositoryURL = null;

	public FrameworkSvnManager(String url, String userName, String pass) throws SVNException {
		repo = getRepository(url,userName,pass);
	}
	
	public FrameworkSvnManager(){
		
	}

	public static SVNRepository getRepository(String url, String userName, String pass) throws SVNException {
		SVNRepository repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(url));
		char[] password = pass.toCharArray();
		ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(userName, password);
		repository.setAuthenticationManager(authManager);
		return repository;
	}

	public static void export(SVNClientManager ourClientManager, String urlSVN, String dstPath, SVNDepth depth)
			throws SVNException {
		SVNUpdateClient updateClient = ourClientManager.getUpdateClient();
		updateClient.setIgnoreExternals(false);
		updateClient.doExport(SVNURL.parseURIEncoded(urlSVN), new File(dstPath), SVNRevision.HEAD, SVNRevision.HEAD,
				null, true, depth);
	}

	public static Collection getEntries(String pathSVN, SVNRepository repository) throws SVNException {

		Collection entries = repository.getDir(pathSVN, -1, null, (Collection) null);

		return entries;

	}

	public static List<SVNDirEntry> getListEntries(String pathSVN, SVNRepository repository) throws SVNException {

		Collection entries = repository.getDir(pathSVN, -1, null, (Collection) null);
		
		List<SVNDirEntry> listEntry = new ArrayList<SVNDirEntry>();
		
		listEntry.addAll(entries);		

		return listEntry;

	}

	public static SVNClientManager createSVNClientManager(SVNRepository repository) {
		SVNClientManager ourClientManager = SVNClientManager.newInstance(null, repository.getAuthenticationManager());
		return ourClientManager;
	}

	public static SVNCommitInfo importDirectory(SVNClientManager ourClientManager, File localPach, String urlSVN,
			String commitMessage, boolean isRecursive) throws SVNException {
		return ourClientManager.getCommitClient().doImport(localPach, SVNURL.parseURIEncoded(urlSVN), commitMessage,
				null, true, true, SVNDepth.INFINITY);
	}

	public static void createWorkingCopy(SVNClientManager ourClientManager, String urlSVN, String dstPath, SVNDepth depth)
			throws SVNException {
		SVNUpdateClient updateClient = ourClientManager.getUpdateClient();
		updateClient.setIgnoreExternals(false);
		updateClient.doCheckout(SVNURL.parseURIEncoded(urlSVN), new File(dstPath), SVNRevision.HEAD, SVNRevision.HEAD,
				depth, true);
	}

	public static void makeDirectory(SVNClientManager ourClientManager, String url, String commitMessage)
			throws SVNException {
		ourClientManager.getCommitClient().doMkDir(new SVNURL[] { SVNURL.parseURIEncoded(url) }, commitMessage);
	}

	public static SVNCommitInfo commitChanged(SVNClientManager ourClientManager, String wcPath, boolean keepLocks,
			String commitMessage) throws SVNException {
		return ourClientManager.getCommitClient().doCommit(new File[] { new File(wcPath) }, keepLocks, commitMessage,
				null, null, false, true, SVNDepth.INFINITY);
	}

	public static long[] updateWorkingCopy(SVNClientManager ourClientManager, String wcPath, SVNRevision revision)
			throws SVNException {
		SVNUpdateClient updateClient = ourClientManager.getUpdateClient();
		updateClient.setIgnoreExternals(false);
		return updateClient.doUpdate(new File[] { new File(wcPath) }, revision, SVNDepth.INFINITY, true, true);
	}

	public static void addEntryWorkingCopy(SVNClientManager ourClientManager, String wcPath) throws SVNException {
		ourClientManager.getWCClient().doAdd(new File(wcPath), true, false, true, SVNDepth.INFINITY, true, true);
	}

	public static void delete(SVNClientManager ourClientManager, String wcPath, boolean force) throws SVNException {
		ourClientManager.getWCClient().doDelete(new File(wcPath), force, false);
	}

	public static void lock(SVNClientManager ourClientManager, String wcPath, boolean isStealLock, String lockComment)
			throws SVNException {
		ourClientManager.getWCClient().doLock(new File[] { new File(wcPath) }, isStealLock, lockComment);
	}

	public static void unLock(SVNClientManager ourClientManager, String wcPath, boolean breakLock) throws SVNException {
		ourClientManager.getWCClient().doUnlock(new File[] { new File(wcPath) }, breakLock);
		
	}
	
	public boolean isLock(SVNClientManager ourClientManager, String FileFullPath) throws SVNException {
		if(getStatus(ourClientManager,FileFullPath).getRemoteLock() == null){
			return false;
		}else{
			return true;
		}
		
	}
	
	public SVNStatus getStatus(SVNClientManager ourClientManager, String FileFullPath)throws SVNException{
		return ourClientManager.getStatusClient().doStatus(new File(FileFullPath), true);
	}

	public static void renameFile(SVNClientManager ourClientManager, String wcPathFile, String newName) throws Exception {
		File oldFile = new File(wcPathFile);
		File newFile = new File(oldFile.getPath().replace(oldFile.getName(), newName));
		oldFile.renameTo(newFile);
		addEntryWorkingCopy(ourClientManager, newFile.getPath());
		delete(ourClientManager, oldFile.getPath(), true);
		commitChanged(ourClientManager, newFile.getPath().replace(newFile.getName(), ""), true,
				"Alterando nomenclatura");
	}
	
	public static void checkoutFile(String workingCopyDirectory, String SVNFile)throws SVNException{
		final SvnOperationFactory svnOperationFactory = new SvnOperationFactory();
		final SvnCheckout checkout = svnOperationFactory.createCheckout();
	    checkout.setSingleTarget(SvnTarget.fromFile(new File(workingCopyDirectory)));
	    checkout.setSource(SvnTarget.fromURL(SVNURL.parseURIEncoded(SVNFile)));
	    
	    //... other options
	    checkout.run();
	}
	
	public static boolean isWorkingCopyRoot(String pathWorkingCopy) throws SVNException{
		SVNWCUtil util = new SVNWCUtil();
		return util.isWorkingCopyRoot(new File(pathWorkingCopy));	
	}
	// http://wiki.svnkit.com/Managing_A_Working_Copy

}
