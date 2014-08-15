package cn.edu.tute.tuteclient.view;

import it.gmariotti.cardslib.library.view.CardView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import cn.edu.tute.tuteclient.R;

import com.actionbarsherlock.app.SherlockActivity;

public class MainViewActivity extends SherlockActivity {
	
	public static void startMainViewActivity(Context context) {
		Intent intent = new Intent(context, MainViewActivity.class);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actitiviy_cards_main);
		
		initCards();
	}
	
	private void initCards() {

	}
	
	private void initClasstableCards() {
		ClasstableCard card = new ClasstableCard(this);
		CardView cardView = (CardView) this.findViewById(R.id.cv_classtable);
		cardView.setCard(card);
	}
}
