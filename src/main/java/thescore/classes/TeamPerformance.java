package thescore.classes;

public class TeamPerformance {

	private Integer score = 0;
	
	private Integer fg = 0;
	private Integer fga = 0;

	private Integer threefg = 0;
	private Integer threefga = 0;

	private Integer ft = 0;
	private Integer fta = 0;

	private Integer ast = 0;
	private Integer stl = 0;
	private Integer blk = 0;

	private Integer def = 0;
	private Integer off = 0;

	private Integer to = 0;
	private Integer foul = 0;
	
	private Integer timeout = 0;

	public Integer getFg() {
		return fg;
	}

	public void setFg(Integer fg) {
		this.fg = fg;
	}

	public Integer getFga() {
		return fga;
	}

	public void setFga(Integer fga) {
		this.fga = fga;
	}

	public Integer getThreefg() {
		return threefg;
	}

	public void setThreefg(Integer threefg) {
		this.threefg = threefg;
	}

	public Integer getThreefga() {
		return threefga;
	}

	public void setThreefga(Integer threefga) {
		this.threefga = threefga;
	}

	public Integer getFt() {
		return ft;
	}

	public void setFt(Integer ft) {
		this.ft = ft;
	}

	public Integer getFta() {
		return fta;
	}

	public void setFta(Integer fta) {
		this.fta = fta;
	}

	public Integer getAst() {
		return ast;
	}

	public void setAst(Integer ast) {
		this.ast = ast;
	}

	public Integer getStl() {
		return stl;
	}

	public void setStl(Integer stl) {
		this.stl = stl;
	}

	public Integer getBlk() {
		return blk;
	}

	public void setBlk(Integer blk) {
		this.blk = blk;
	}

	public Integer getDef() {
		return def;
	}

	public void setDef(Integer def) {
		this.def = def;
	}

	public Integer getOff() {
		return off;
	}

	public void setOff(Integer off) {
		this.off = off;
	}

	public Integer getTo() {
		return to;
	}

	public void setTo(Integer to) {
		this.to = to;
	}

	public Integer getFoul() {
		return foul;
	}

	public void setFoul(Integer foul) {
		this.foul = foul;
	}

	public Integer getScore() {
		return score;
	}
	
	public void updateScore(){
		score = getFt() + getThreefg() + (getFg() * 2);
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

}
