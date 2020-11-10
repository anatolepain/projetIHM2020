package fr.iutfbleau.projetIHM2020FI2.CONTROLEUR;
import fr.iutfbleau.projetIHM2020FI2.VUE.*;
import fr.iutfbleau.projetIHM2020FI2.API.*;
import fr.iutfbleau.projetIHM2020FI2.MODEL.*;
import fr.iutfbleau.projetIHM2020FI2.MNP.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class PieceController 
{
	private static CasePieceEvent[] casePEvent;
	private static CasePiece[] caseP;
	private PieceContenuEvent[] ramasserEvent;
	
	//model
	private Joueur jP;
	private Piece piecePos;
	private static Map<CasePiece, Truc> mapCasePieceTruc = new HashMap<CasePiece, Truc>();

	public PieceController()
	{
		this.casePEvent = new CasePieceEvent[PieceContenuVue.getNbCase()];
		this.ramasserEvent = new PieceContenuEvent[PieceContenuVue.getNbCase()];
		this.caseP = PieceContenuVue.getCasePiecetab();
		this.jP = SetupModel.getJoueur();

		Iterator<Truc> pT = jP.getPiece().getTrucs();

		for(int i = 0 ; i < this.casePEvent.length;i++)
		{
			this.casePEvent[i] = new CasePieceEvent(this.caseP[i]);
			this.caseP[i].addMouseListener(this.casePEvent[i]);

			//Ramasser
			this.ramasserEvent[i] = new PieceContenuEvent(this.caseP[i]);
			this.caseP[i].GetPopupMenu().getMenuItemRamasser().addActionListener(this.ramasserEvent[i]);
			if(pT.hasNext()){
				mapCasePieceTruc.put(this.caseP[i], pT.next());
			}
		}
		this.piecePos = this.jP.getPiece();
		if(this.piecePos != null)
		{
			PieceController.changePiece(this.piecePos);
		}

	}

	public static void changePiece(Piece p)
	{
		if(p != null)
		{
			PieceContenuVue.clear();
			Iterator<Truc> pT = p.getTrucs();
			int i = 0;
			while(pT.hasNext())
			{
				Truc t = pT.next();
				PieceContenuVue.addTruc(t.getTypeTruc(),t.getDescription());
				mapCasePieceTruc.put(caseP[i],t);
				i++;
			}
		}
	}

	public static void ramasserPiece(CasePiece cp){
		Truc t = mapCasePieceTruc.get(cp);
		SetupModel.getJoueur().getPiece().removeTruc(t);
		cp.clearObject();
	}
}