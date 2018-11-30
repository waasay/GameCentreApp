package group_0548.gamecentre.twentygame;

/*
Adapted from:
https://github.com/DaveNOTDavid/sample-puzzle/blob/master/app/src/main/java/com/davenotdavid/samplepuzzle/GestureDetectGridView.java

This extension of GridView contains built in logic for handling swipes between buttons
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.GridView;
import android.widget.Toast;

public class TwentyGestureDetectGridView extends GridView {
    private static final int SWIPE_MIN_DISTANCE = 100;
    private static final int SWIPE_MAX_OFF_PATH = 100;
    private static final int SWIPE_THRESHOLD_VELOCITY = 100;
    private GestureDetector gDetector;
    private boolean mFlingConfirmed = false;
    private float mTouchX;
    private float mTouchY;
    private TwentyMovementController mController;
    private TwentyManager twentyManager;

    public TwentyGestureDetectGridView(Context context) {
        super(context);
        init(context);
    }

    public TwentyGestureDetectGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TwentyGestureDetectGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(final Context context) {
        mController = new TwentyMovementController();
        gDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent event) {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                   float velocityY) {


                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH) {
                    if (Math.abs(e1.getX() - e2.getX()) > SWIPE_MAX_OFF_PATH
                            || Math.abs(velocityY) < SWIPE_THRESHOLD_VELOCITY) {
                        return false;
                    }
                    if (checkGameSituation(context).equals("Neither")) {
                        if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE) {
                            mController.processUpMovement(); //up
                        } else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE) {
                            mController.processDownMovement(); //down
                        }
                    }

                } else {
                    if (Math.abs(velocityX) < SWIPE_THRESHOLD_VELOCITY) {
                        return false;
                    }
                    if (checkGameSituation(context).equals("Neither")) {
                        if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE) {
                            mController.processLeftMovement(); //left

                        } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE) {
                            mController.processRightMovement(); //right
                        }
                    }
                }

                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        gDetector.onTouchEvent(ev);

        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mFlingConfirmed = false;
        } else if (action == MotionEvent.ACTION_DOWN) {
            mTouchX = ev.getX();
            mTouchY = ev.getY();
        } else {

            if (mFlingConfirmed) {
                return true;
            }

            float dX = (Math.abs(ev.getX() - mTouchX));
            float dY = (Math.abs(ev.getY() - mTouchY));
            if ((dX > SWIPE_MIN_DISTANCE) || (dY > SWIPE_MIN_DISTANCE)) {
                mFlingConfirmed = true;
                return true;
            }
        }

        return super.onInterceptTouchEvent(ev);
    }


    /**
     * Initialize the undo event
     */
    public void undoEvent() {
        this.mController.processUndo(this.getContext());
    }

    /**
     * Initialize the redo event
     */
    public void redoEvent() {
        this.mController.processRedo(this.getContext());
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return gDetector.onTouchEvent(ev);
    }

    public void setTwentyManager(TwentyManager twentyManager) {
        this.twentyManager = twentyManager;
        mController.setTwentyManager(twentyManager);
    }

    String checkGameSituation(Context context) {
        if (twentyManager.puzzleSolved()) {
            Toast.makeText(context, "You Win!", Toast.LENGTH_SHORT).show();
            return "Win";
        } else if (twentyManager.puzzleLost()) {
            Toast.makeText(context, "You Lost!", Toast.LENGTH_SHORT).show();
            return "Lost";
        }
        return "Neither";
    }
}