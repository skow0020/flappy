package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import org.omg.PortableInterceptor.Interceptor;

import java.util.Random;

public class flappy extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture[] birds;
	Texture topTube;
	Texture bottomTube;
	BitmapFont font;
	int flapstate = 0;
	float birdY = 0;
	float velocity = 0;
	int timer = 0;
	int gameState = 0;
	int score = 0;
	float midY = 0;
	float tubeX = 0;
	float gap = 500;
	Circle birdCircle;
	Rectangle recTop;
	Rectangle recBottom;
	Random randomGenerator;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("bg.png");
		birds = new Texture[2];
		birds[0] = new Texture("bird.png");
		birds[1] = new Texture("bird2.png");
		topTube = new Texture("toptube.png");
		bottomTube = new Texture("bottomtube.png");
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(10);
		reset();

	}

	public void reset()
	{
		birdY = Gdx.graphics.getHeight() / 2 - birds[0].getHeight() / 2;
		midY = Gdx.graphics.getHeight() / 2;
		tubeX = Gdx.graphics.getWidth() / 2 - topTube.getWidth() / 2;
		randomGenerator = new Random();
		birdCircle = new Circle();
		recTop = new Rectangle();
		recBottom = new Rectangle();
		velocity = 0;
		score = 0;
	}

	public void gameOver(){
		gameState = 0;
		batch.begin();
		font.draw(batch, "GAME OVER", 100, 500);
		batch.end();
		if (Gdx.input.justTouched()) {
			reset();
		}
	}


	@Override
	public void render () {
		if (gameState != 0) {
			if (Gdx.input.justTouched()) {
				velocity = -20;

			}
			if (birdY > 0 || velocity < 0)
			{
				birdY -= velocity;
				velocity++;
			}
			if (tubeX + bottomTube.getWidth() > 0)
			{
				tubeX-=10;
			}
			else
			{
				tubeX = Gdx.graphics.getWidth();
				midY = randomGenerator.nextInt(Gdx.graphics.getHeight() - 500) + 250;
			}
		}
		else if (Gdx.input.justTouched())
		{
			gameState = 1;
		}

		timer++;
		if (timer == 10) {
			if (flapstate == 0) {
				flapstate = 1;
			} else {
				flapstate = 0;
			}
			timer = 0;
		}

		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(birds[flapstate], Gdx.graphics.getWidth() / 2 - birds[flapstate].getWidth() / 2, birdY);
		batch.draw(topTube, tubeX, midY + gap / 2);
		batch.draw(bottomTube, tubeX, midY - bottomTube.getHeight() - gap / 2);
		font.draw(batch, String.valueOf(score), 100, 200);
		batch.end();

		birdCircle.set(Gdx.graphics.getWidth() / 2, birdY + birds[flapstate].getHeight() / 2, birds[flapstate].getWidth() / 2);
		recTop.set(tubeX, midY + gap / 2, topTube.getWidth(), topTube.getHeight());
		recBottom.set(tubeX, midY - bottomTube.getHeight() - gap / 2, bottomTube.getWidth(), bottomTube.getHeight());

		if (tubeX < Gdx.graphics.getWidth() / 2 && tubeX >= Gdx.graphics.getWidth() / 2 - 10)
		{
			score++;
		}
		if (Intersector.overlaps(birdCircle, recBottom) || Intersector.overlaps(birdCircle, recTop))
		{
			gameOver();
		}
	}
}
