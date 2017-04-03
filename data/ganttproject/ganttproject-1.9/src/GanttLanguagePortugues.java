/***************************************************************************
                           GanttLanguagePortugues.java  -  description
                             -------------------
    begin                : dec 2002
    copyright            : (C) 2002 by Thomas Alexandre
    email                : alex.thomas@netcourrier.com
	help by              : Nelson Ferraz  nferraz@phperl.com
 ***************************************************************************/

/***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/

import java.lang.String;

/**
  * Portugues class Language
  */
public class GanttLanguagePortugues extends GanttLanguage
{

	public String getLanguage() { return "Portugues"; }

	public String appliTitle() { return "GanttProject"; }

	public String getMonth (int m)
	{
		switch(m)
		{
			case 0: return "Janeiro";
			case 1: return "Fevereiro";
			case 2: return "Mar�o";
			case 3: return "Abril";
			case 4: return "Maio";
			case 5: return "Junho";
			case 6: return "Julho";
			case 7: return "Agosto";
			case 8: return "Setembro";
			case 9: return "Outubro";
			case 10:return "Novembro";
		}
		return "Dezembro";
	}

	public String getDay (int d)
	{
		switch(d)
		{
			case 1: return "Segunda";
			case 2: return "Ter�a";
			case 3: return "Quarta";
			case 4: return "Quinta";
			case 5: return "Sexta";
			case 6: return "S�bado";
		}
		return "Domingo";

	}

	public String week () { return "Semana"; }

	public String getOk() { return "Ok"; }

	public String getCancel() { return "Cancelar"; }
	
	public String getYes() { return "Sim"; }
	
	public String getNo() { return "N�o"; }

	public String error() { return "Erro"; }

	public String warning() { return "Aten��o"; }

//////////////////////////////////////////////////////////////////////////

	public String project () { return "Projeto"; }

	public String newProject () { return "Novo"; }

	public String openProject () { return "Abrir"; }

	public String saveProject () { return "Salvar"; }

	public String saveAsProject () { return "Salvar como"; }
	
	public String export () { return "Exportar..."; }

	public String printProject () { return "Imprimir..."; }

	public String ProjectProperties () { return "Propriedades..."; }

	public String quit () { return "Sair"; }

//////////////////////////////////////////////////////////////////////////

	public String task () { return "Tarefa"; }

	public String createTask () { return "Nova tarefa"; }

	public String deleteTask () { return "Deletar"; }

	public String propertiesTask () { return "Propriedades..."; }

	public String notesTask () { return "Notas"; }

	public String upTask () { return "Acima"; }

	public String downTask () { return "Abaixo"; }

//////////////////////////////////////////////////////////////////////////
	
	public String human () { return "Pessoa"; }
	
	public String newHuman () { return "Nova pessoa"; }

	public String deleteHuman () { return "Deletar pessoa"; }

	public String propertiesHuman () { return "Propriedades..."; }

//////////////////////////////////////////////////////////////////////////

	public String language () { return "Linguagem"; }

//////////////////////////////////////////////////////////////////////////

	public String help () { return "Ajuda"; }

	public String manual () { return "Manual"; }

	public String webPage () { return "P�gina na web"; }

	public String javadoc () { return "Javadoc"; }

	public String about () { return "Sobre..."; }

//////////////////////////////////////////////////////////////////////////

	public String backDate () { return "Anterior"; }

	public String forwardDate () { return "Pr�ximo"; }

	public String zoomIn () { return "Aumentar zoom"; }

	public String zoomOut () { return "Reduzir zoom"; }

//////////////////////////////////////////////////////////////////////////

	public String propertiesFor () { return "Propriedades para "; }

	public String newTask () { return "Nova tarefa"; }

	public String notesFor () { return "Notas para "; }

	public String chooseDate () { return "Escolha uma data"; }

	public String name () { return "Nome"; }

	public String motherTask () { return "Tarefa-m�e"; }

	public String none () { return "Nenhuma"; }

	public String dateOfBegining () { return "Data de in�cio"; }

	public String length () { return "Dura��o"; }

	public String meetingPoint () { return "Ponto de encontro"; }

	public String depends () { return "Depend�ncias"; }

	public String advancement () { return "Avan�o"; }

	public String putDate () { return "Coloque a data e hora"; }

	public String propertiesMsg (String taskName) { return "Uma tarefa j� possui esse nome ("+taskName+")"; }

//////////////////////////////////////////////////////////////////////////

	public String [] getPersonFunction()
	{
		String [] res = {
			"Indefinido",
			"Gerente de Projeto",
			"Desenvolvedor",
			"Escritor de documenta��o",	 
			"Testador",
			"Designer Gr�fico",
			"Tradutor de documenta��o",
			"Empacotador (.rpm, .tgz ...)",
			"Analista",
			"Web Designer",
			"Sem papel espec�fico"
		};
		return res;
	}
	
	public String [] getColsName()
	{
		String[] columnNames = {"Nome", "Fun��o", "Contato" };
		return columnNames;
	}

//////////////////////////////////////////////////////////////////////////
	
	public String chef() { return "Chef"; }

	public String organization() { return "Organiza��o"; }
	
	public String shortDescription() { return "Descri��o"; }

//////////////////////////////////////////////////////////////////////////

	public String msg1() { return "Voc� gostaria de salvar o projeto antes??"; }

	public String msg2() { return "N�o consegui abrir o arquivo "; }

	public String msg3() { return "Deseja realmente deletar a tarefa "; }

	public String msg4() { return "N�o consegui abrir o netscape.\n Abra seu browser em http://ganttproject.sourceforge.net"; }

	public String msg5() { return "N�o consegui abrir o netscape.\n Abra seu browser em ../doc/index.html"; }
	
	public String msg6() { return "Deseja realmente deletar a pessoa "; }
	
}




