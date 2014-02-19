/**
 * 
 */
package demos;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.Log;

import AGFX.F3D.F3D;
import AGFX.F3D.AppWrapper.TF3D_AppWrapper;
import AGFX.F3D.Config.TF3D_Config;

/**
 * @author AndyGFX
 * 
 */
public class Demo_SlickScrollDemo extends TF3D_AppWrapper
{

	/** The size of the tank sprite - used for finding the centre */
	private static final int   TANK_SIZE         = 32;
	/** The size of the tiles - used to determine the amount to draw */
	private static final int   TILE_SIZE         = 32;
	/** The speed the tank moves at */
	private static final float TANK_MOVE_SPEED   = 0.003f;
	/** The speed the tank rotates at */
	private static final float TANK_ROTATE_SPEED = 0.2f;

	/** The player's x position in tiles */
	private float              playerX           = 15;
	/** The player's y position in tiles */
	private float              playerY           = 16;

	/** The width of the display in tiles */
	private int                widthInTiles;
	/** The height of the display in tiles */
	private int                heightInTiles;

	/** The offset from the centre of the screen to the top edge in tiles */
	private int                topOffsetInTiles;
	/** The offset from the centre of the screen to the left edge in tiles */
	private int                leftOffsetInTiles;

	/** The map that we're going to drive around */
	private TiledMap           map;

	/** The animation representing the player's tank */
	private Animation          player;

	/** The angle the player is facing */
	private float              ang;
	/** The x component of the movement vector */
	private float              dirX;
	/** The y component of themovement vector */
	private float              dirY;

	/**
	 * The collision map indicating which tiles block movement - generated based
	 * on tile properties
	 */
	private boolean[][]        blocked;

	public Demo_SlickScrollDemo()
	{
	}

	@Override
	public void onConfigure()
	{
		try
		{

			// Redefine Config

			F3D.Config = new TF3D_Config();

			F3D.Config.r_display_width = 800;
			F3D.Config.r_display_height = 600;
			F3D.Config.r_fullscreen = false;
			F3D.Config.r_display_vsync = true;
			F3D.Config.r_display_title = "jFinal3D Graphics Engine 2010 - app" + this.getClass().getName();

			super.onConfigure();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void onInitialize()
	{
		
		F3D.Slick.graphics.setAntiAlias(true);

		// load the sprites and tiles, note that underneath the texture
		// will be shared between the sprite sheet and tilemap
		SpriteSheet sheet = null;
		try
		{
			sheet = new SpriteSheet("media/scroller/sprites.png", 32, 32);
		} catch (SlickException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// load the tilemap created the TileD tool
		try
		{
			map = new TiledMap("media/scroller/map.tmx");
		} catch (SlickException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// build a collision map based on tile properties in the TileD map
		blocked = new boolean[map.getWidth()][map.getHeight()];
		for (int x = 0; x < map.getWidth(); x++)
		{
			for (int y = 0; y < map.getHeight(); y++)
			{
				int tileID = map.getTileId(x, y, 0);
				String value = map.getTileProperty(tileID, "blocked", "false");
				if ("true".equals(value))
				{
					blocked[x][y] = true;
				}
			}
		}

		// caculate some layout values for rendering the tilemap. How many tiles
		// do we need to render to fill the screen in each dimension and how far
		// is
		// it from the centre of the screen
		widthInTiles = F3D.Config.r_display_width / TILE_SIZE;
		heightInTiles = F3D.Config.r_display_height / TILE_SIZE;
		topOffsetInTiles = heightInTiles / 2;
		leftOffsetInTiles = widthInTiles / 2;

		// create the player sprite based on a set of sprites from the sheet
		// loaded
		// above (tank tracks moving)
		player = new Animation();
		for (int frame = 0; frame < 7; frame++)
		{
			player.addFrame(sheet.getSprite(frame, 1), 150);
		}
		player.setAutoUpdate(false);

		// update the vector of movement based on the initial angle
		updateMovementVector();

		Log.info("Window Dimensions in Tiles: " + widthInTiles + "x" + heightInTiles);
	}

	/**
	 * Check if a specific location of the tank would leave it on a blocked tile
	 * 
	 * @param x
	 *            The x coordinate of the tank's location
	 * @param y
	 *            The y coordinate of the tank's location
	 * @return True if the location is blocked
	 */
	private boolean blocked(float x, float y)
	{
		return blocked[(int) x][(int) y];
	}

	/**
	 * Try to move in the direction specified. If it's blocked, try sliding. If
	 * that doesn't work just don't bother
	 * 
	 * @param x
	 *            The amount on the X axis to move
	 * @param y
	 *            The amount on the Y axis to move
	 * @return True if we managed to move
	 */
	private boolean tryMove(float x, float y)
	{
		float newx = playerX + x;
		float newy = playerY + y;

		// first we try the real move, if that doesn't work
		// we try moving on just one of the axis (X and then Y)
		// this allows us to slide against edges
		if (blocked(newx, newy))
		{
			if (blocked(newx, playerY))
			{
				if (blocked(playerX, newy))
				{
					// can't move at all!
					return false;
				} else
				{
					playerY = newy;
					return true;
				}
			} else
			{
				playerX = newx;
				return true;
			}
		} else
		{
			playerX = newx;
			playerY = newy;
			return true;
		}
	}

	/**
	 * Update the direction that will be moved in based on the current angle of
	 * rotation
	 */
	private void updateMovementVector()
	{
		dirX = (float) Math.sin(Math.toRadians(ang));
		dirY = (float) -Math.cos(Math.toRadians(ang));
	}

	/**
	 * @see org.newdawn.slick.Game#render(org.newdawn.slick.GameContainer,
	 *      org.newdawn.slick.Graphics)
	 */
	public void render() 
	{
		// draw the appropriate section of the tilemap based on the centre
		// (hence the -(TANK_SIZE/2)) of
		// the player
		int playerTileX = (int) playerX;
		int playerTileY = (int) playerY;

		// caculate the offset of the player from the edge of the tile. As the
		// player moves around this
		// varies and this tells us how far to offset the tile based rendering
		// to give the smooth
		// motion of scrolling
		int playerTileOffsetX = (int) ((playerTileX - playerX) * TILE_SIZE);
		int playerTileOffsetY = (int) ((playerTileY - playerY) * TILE_SIZE);

		// render the section of the map that should be visible. Notice the -1
		// and +3 which renders
		// a little extra map around the edge of the screen to cope with tiles
		// scrolling on and off
		// the screen
		map.render(playerTileOffsetX - (TANK_SIZE / 2), playerTileOffsetY - (TANK_SIZE / 2), playerTileX - leftOffsetInTiles - 1, playerTileY - topOffsetInTiles - 1, widthInTiles + 3, heightInTiles + 3);

		// draw entities relative to the player that must appear in the centre
		// of the screen
		F3D.Slick.graphics.translate(400 - (int) (playerX * 32), 300 - (int) (playerY * 32));

		drawTank(F3D.Slick.graphics, playerX, playerY, ang);
		// draw other entities here if there were any

		F3D.Slick.graphics.resetTransform();
	}

	/**
	 * Draw a single tank to the game
	 * 
	 * @param g
	 *            The graphics context on which we're drawing
	 * @param xpos
	 *            The x coordinate in tiles the tank is at
	 * @param ypos
	 *            The y coordinate in tiles the tank is at
	 * @param rot
	 *            The rotation of the tank
	 */
	public void drawTank(Graphics g, float xpos, float ypos, float rot)
	{
		// work out the centre of the tank in rendering coordinates and then
		// spit onto the screen
		int cx = (int) (xpos * 32);
		int cy = (int) (ypos * 32);
		g.rotate(cx, cy, rot);
		player.draw(cx - 16, cy - 16);
		g.rotate(cx, cy, -rot);
	}

	@Override
	public void onUpdate3D()
	{

	}

	@Override
	public void onUpdate2D()
	{

		int delta = F3D.Timer.GetDelta();

		// check the controls, left/right adjust the rotation of the tank,
		// up/down
		// move backwards and forwards

		if (F3D.Input.Key.IsKeyDown(Keyboard.KEY_LEFT))
		{
			ang -= delta * TANK_ROTATE_SPEED;
			updateMovementVector();
		}
		if (F3D.Input.Key.IsKeyDown(Keyboard.KEY_RIGHT))
		{
			ang += delta * TANK_ROTATE_SPEED;
			updateMovementVector();
		}
		if (F3D.Input.Key.IsKeyDown(Keyboard.KEY_UP))
		{
			if (tryMove(dirX * delta * TANK_MOVE_SPEED, dirY * delta * TANK_MOVE_SPEED))
			{
				// if we managed to move update the animation
				player.update((long) delta);
			}
		}
		if (F3D.Input.Key.IsKeyDown(Keyboard.KEY_DOWN))
		{
			if (tryMove(-dirX * delta * TANK_MOVE_SPEED, -dirY * delta * TANK_MOVE_SPEED))
			{
				// if we managed to move update the animation
				player.update(delta);
			}
		}

		F3D.Slick.graphics.setDrawMode(Graphics.MODE_NORMAL);
		this.render();
	}

	@Override
	public void OnDestroy()
	{

	}

	public static void main(String[] args)
	{

		new Demo_SlickScrollDemo().Execute();
		System.exit(0);

	}

}
