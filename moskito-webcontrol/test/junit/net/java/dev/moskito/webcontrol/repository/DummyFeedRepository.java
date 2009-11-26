package net.java.dev.moskito.webcontrol.repository;

import java.util.List;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class DummyFeedRepository {
	
	private RepositoryImpl repository;
	
	@Before public void init(){
		repository = new RepositoryImpl();
	}
	
	private void fillRepository(){
		repository.addSnapshot("TestMem", createSnapshot(1));
		repository.addSnapshot("TestMem", createSnapshot(2));
		repository.addSnapshot("TestMem", createSnapshot(3));
	}
	
	private Snapshot createSnapshot(int source){
		Snapshot snapshot = new Snapshot(new SnapshotSource("Server "+source));
		
		snapshot.addAttribute(AttributeFactory.create(AttributeType.LONG, "Eden.FreeMB", getFree(50, source)));
		snapshot.addAttribute(AttributeFactory.create(AttributeType.LONG, "Eden.UsedMB", getUsed(50, source)));
		snapshot.addAttribute(AttributeFactory.create(AttributeType.LONG, "Eden.MaxUsedMB", getMaxUsed(50, source)));
		snapshot.addAttribute(AttributeFactory.create(AttributeType.LONG, "Eden.CommitedMB", ""+getCommited(50, source)));
		
		snapshot.addAttribute(AttributeFactory.create(AttributeType.LONG, "Survivor.FreeMB", getFree(25, source)));
		snapshot.addAttribute(AttributeFactory.create(AttributeType.LONG, "Survivor.UsedMB", getUsed(25, source)));
		snapshot.addAttribute(AttributeFactory.create(AttributeType.LONG, "Survivor.MaxUsedMB", getMaxUsed(25, source)));
		snapshot.addAttribute(AttributeFactory.create(AttributeType.LONG, "Survivor.CommitedMB", ""+getCommited(25, source)));

		snapshot.addAttribute(AttributeFactory.create(AttributeType.LONG, "OldGen.FreeMB", getFree(512, source)));
		snapshot.addAttribute(AttributeFactory.create(AttributeType.LONG, "OldGen.UsedMB", getUsed(512, source)));
		snapshot.addAttribute(AttributeFactory.create(AttributeType.LONG, "OldGen.MaxUsedMB", getMaxUsed(512, source)));
		snapshot.addAttribute(AttributeFactory.create(AttributeType.LONG, "OldGen.CommitedMB", ""+getCommited(512, source)));

		snapshot.addAttribute(AttributeFactory.create(AttributeType.LONG, "PermGen.FreeMB",  getFree(128, source)));
		snapshot.addAttribute(AttributeFactory.create(AttributeType.LONG, "PermGen.UsedMB", getUsed(128, source)));
		snapshot.addAttribute(AttributeFactory.create(AttributeType.LONG, "PermGen.MaxUsedMB", getMaxUsed(128, source)));
		snapshot.addAttribute(AttributeFactory.create(AttributeType.LONG, "PermGen.CommitedMB", ""+getCommited(128, source)));
		
		
		return snapshot;
	}
	
	private double getVariant(int source){
		return 0.20 * source;
	}
	
	private String getFree(int init, int source){
		return ""+(int) (0.5*getCommited(init, source));
	}
	
	private String getMaxUsed(int init, int source){
		return ""+(int) (0.8*getCommited(init, source));
	}
	
	private String getUsed(int init, int source){
		return ""+(int) (0.5*getCommited(init, source));
	}

	private int getCommited(int init, int source){
		return(int) (((double)init)*getVariant(source));
	}

	@Test public void testRepository(){
		fillRepository();
		
		List<Snapshot> snapshots = repository.getSnapshots("TestMem");
		assertEquals(3, snapshots.size());
		
	}
}
