package lt.javainiai.utils;

public class SpoiledResults {
	
	private Long spoiledSingle;
	private Long spoiledMulti;
	
	public SpoiledResults() {
		
	}
	
	public SpoiledResults(Long spoiledSingle, Long spoiledMulti) {
		this.spoiledSingle = spoiledSingle;
		this.spoiledMulti = spoiledMulti;
	}
	
	public Long getSpoiledSingle() {
		return spoiledSingle;
	}
	public void setSpoiledSingle(Long spoiledSingle) {
		this.spoiledSingle = spoiledSingle;
	}
	public Long getSpoiledMulti() {
		return spoiledMulti;
	}
	public void setSpoiledMulti(Long spoiledMulti) {
		this.spoiledMulti = spoiledMulti;
	}
	
	

}
