package util;

public enum ColumnEnum {
	
	Id(0), Nome(1), Editar(2), Remover(3), Telefones(4), Emails(5);
	
	private final int valor;
	
	ColumnEnum(int valorOpcao) {
		valor = valorOpcao;
	}
	
	public int getValor() {
		return valor;
	} 
	
}
