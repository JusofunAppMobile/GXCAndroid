package com.jusfoun.jusfouninquire.ui.widget.shareholder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.util.PhoneUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaoyapeng
 * @version create time:17/12/1920:18
 * @Email zyp@jusfoun.com
 * @Description ${股东穿透}
 */
public class ShareHolderPenetrateView extends View {
    private PointF cenPointF;
    private Paint mCirPaint, pathPaint, mRtextPaint;
    private Context mContext;


    private Path mPath;


    protected VelocityTracker mVelocityTracker;

    protected ArrayList<RectFModel> rectfBgList = new ArrayList<RectFModel>();

    private float scaleX, scaleY;// 缩放时 x,y坐标


    private int mItemHeight = 0;// 每一项的高度
    private int mItemWidth = 0; //每一项的宽度

    private int startTop = 0;//组件内部开始绘制的上边距
    private int intervalHeight = 0;//每个item 之间的高度间距
    private int intervalWidth = 0;//每个item 之间的宽度间距


    private int firstCount = 0;
    private int secondeCount = 0;
    private int thirdCount = 0;


    private int firstCountHasNext = 0;
    private int secondeCountCountHasNext = 0;


    private List<RectFModel> thirdList;
    private List<RectFModel> secondeList;
    private List<RectFModel> firstList;

    private int TEXT_SIZE = 0;

    private int pointType = -1;

    private  int TYPE_TRANSLATE =0;
    private int TYPE_SCALE = 1;

    public ShareHolderPenetrateView(Context context) {
        super(context);
        mContext = context;
        initData();
        initViews();
        initAtions();
    }

    public ShareHolderPenetrateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initData();
        initViews();
        initAtions();
    }

    public ShareHolderPenetrateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initData();
        initViews();
        initAtions();
    }

    private void initData() {
        TEXT_SIZE = PhoneUtil.dip2px(mContext,14);

        mPath = new Path();
        mTran = new float[]{0, 0};

        cenPointF = new PointF();

        mCirPaint = new Paint();
//        mCirPaint.setColor(Color.parseColor("#FF7200"));
        mCirPaint.setStrokeWidth(2);
        mCirPaint.setStyle(Paint.Style.FILL);

        pathPaint = new Paint();
        pathPaint.setColor(Color.parseColor("#9b9b9b"));
        pathPaint.setStrokeWidth(PhoneUtil.dip2px(mContext, 1));
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setAntiAlias(true);
        pathPaint.setPathEffect(new DashPathEffect(new float[]{4, 4}, 0));


        mRtextPaint = new Paint();
        mRtextPaint.setColor(Color.parseColor("#ffffff"));
        mRtextPaint.setTextSize(TEXT_SIZE);
        mRtextPaint.setStyle(Paint.Style.STROKE);
//        mRtextPaint.setTextAlign(Paint.Align.CENTER);


        mVelocityTracker = VelocityTracker.obtain();


        mItemHeight = PhoneUtil.dip2px(mContext,44);
        mItemWidth = PhoneUtil.dip2px(mContext,210);
        startTop = PhoneUtil.dip2px(mContext,22);
        intervalHeight = PhoneUtil.dip2px(mContext,24);
        intervalWidth = PhoneUtil.dip2px(mContext,100);



        thirdList = new ArrayList<>();
        secondeList = new ArrayList<>();
        firstList = new ArrayList<>();
    }

    private void initViews() {

    }

    private void initAtions() {
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        cenPointF.x = w / 2;
        cenPointF.y = h / 2;
        scaleX = cenPointF.x;
        scaleY = cenPointF.y;

        setData();
    }


    boolean isDrawFinish = false;

    private Matrix invertMatrix = new Matrix();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(mTran[0], mTran[1]);
        canvas.scale(scaleCount, scaleCount, cenPointF.x-mTran[0] , cenPointF.y-mTran[1]  );
        canvas.getMatrix().invert(invertMatrix);

        for (int j = 0; j < rectfBgList.size(); j++) {

            RectFModel pm = rectfBgList.get(j);

            LinearGradient linearGradient = new LinearGradient(pm.rectF.left, 0, pm.rectF.right, 0, new int[]{pm.startColor, pm.endColor}, null, LinearGradient.TileMode.CLAMP);
            mCirPaint.setShader(linearGradient);
            canvas.drawRoundRect(pm.rectF, PhoneUtil.dip2px(mContext, 2), PhoneUtil.dip2px(mContext, 2), mCirPaint);


            canvas.save();
            if (!TextUtils.isEmpty(pm.tag) && pm.textPoint != null) {
//                if(pm.isFirst){
//                    canvas.save();
//                    TextPaint tp = new TextPaint();
//                    tp.set(mRtextPaint);
//
//                    canvas.translate(pm.textPoint.x, pm.textPoint.y);
//
//                    StaticLayout myStaticLayout = new StaticLayout(pm.tag, tp, mItemWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
//                    myStaticLayout.draw(canvas);
//                    canvas.restore();
//                }else {
//                    canvas.drawText(pm.tag, pm.textPoint.x, pm.textPoint.y, mRtextPaint);
//                }

                canvas.save();
                TextPaint tp = new TextPaint();
                tp.set(mRtextPaint);

                if (j != rectfBgList.size() - 1) {

                    String s = pm.tag;
                    if (pm.tag.length() > 28) {
                        s = pm.tag.substring(0, 28) + "...";
                    }


                    if (  s.length() > 28) {
                        canvas.translate(pm.textPoint.x, pm.textPoint.y - TEXT_SIZE * 1.5f);
                    } else {
                        canvas.translate(pm.textPoint.x, pm.textPoint.y - TEXT_SIZE);
                    }
                    StaticLayout myStaticLayout = new StaticLayout(s, tp, mItemWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
                    myStaticLayout.draw(canvas);
                } else {
                    String s = pm.tag;
                    if (pm.tag.length() > 11) {
                        s = pm.tag.substring(0, 10) + "...";
                    }

                    canvas.translate(pm.textPoint.x + (mItemHeight - TEXT_SIZE) / 2, pm.textPoint.y);
                    StaticLayout myStaticLayout = new StaticLayout(s, tp, TEXT_SIZE, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
                    myStaticLayout.draw(canvas);
                }


                canvas.restore();
            }
            canvas.restore();


            mPath.reset();

            if (pm.pathPointF1 != null) {
                mPath.moveTo(pm.pathPointF1.x, pm.pathPointF1.y);
                mPath.lineTo(pm.pathPointF2.x, pm.pathPointF2.y);
                mPath.lineTo(pm.pathPointF3.x, pm.pathPointF3.y);
                mPath.lineTo(pm.pathPointF4.x, pm.pathPointF4.y);
                canvas.drawPath(mPath, pathPaint);
                mPath.close();
            }
        }

        canvas.restore();
    }


    private boolean doublePointer;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        if (!enableTouch)
//            return true;
//        if (onTouchView != null)
//            onTouchView.onTouch();

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (doublePointer)
                    doublePointer = false;
                if (isMove) {
                    isMove = false;
                    break;
                }

                float[] f = {event.getX(), event.getY()};
                invertMatrix.mapPoints(f);
                RectFModel rectFModel = isInRect(f[0], f[1], rectfBgList);
                if (rectFModel != null) {
                    Toast.makeText(mContext, rectFModel.tag, Toast.LENGTH_SHORT).show();
                }
        }

        switch (event.getPointerCount()) {
            case 1:
                Log.e("tag","getPointerCountgetPointerCount1");
                translate(event);
                break;
            case 2:
                Log.e("tag","getPointerCountgetPointerCount2");
                scale(event);
                break;
        }
        return true;
    }

    protected float mTran[];
    protected float downX, downY, initX, initY;

    protected void translate(MotionEvent event) {
        if (doublePointer) {
            return;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                initX = downX;
                initY = downY;
                break;
            case MotionEvent.ACTION_MOVE:
                if ((Math.abs(event.getX() - initX)) < 5 && (Math.abs(event.getY() - initY) < 5))
                    isMove = false;
                else
                    isMove = true;
                mTran[0] = mTran[0] + (event.getX() - downX)/scaleCount;
                mTran[1] = mTran[1] + (event.getY() - downY)/scaleCount;
                downX = event.getX();
                downY = event.getY();
                pointType = TYPE_TRANSLATE;
                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
    }

    protected boolean isMove;
    float lastScale = 0.5f;
    protected float x3;
    protected float scaleCount = 1f;
    protected float minit = 0;

    protected void scale(MotionEvent event) {
        doublePointer = true;
        mVelocityTracker.addMovement(event);
        mVelocityTracker.computeCurrentVelocity(2000);
        isMove = true;
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_POINTER_UP:
                lastScale = scaleCount;
                break;
            case MotionEvent.ACTION_MOVE:
                float toScal = getXYLength(event);
                x3 = toScal / minit;
                float v = mVelocityTracker.getXVelocity();
                if (Math.abs(v) < 5)
                    break;
                if (x3 < 1 && scaleCount <= 0.3)
                    break;
                scaleCount = x3 - 1 + lastScale;

                cenPointF.x = (event.getX(0)+event.getX(1))/2;
                cenPointF.y = (event.getY(0)+event.getY(1))/2;

//                Log.e("tag","cenPointFcenPointF="+cenPointF.x+" "+cenPointF.y+" "+mTran[0]);

                pointType = TYPE_SCALE;
                postInvalidate();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                if (event.getPointerCount() == 2) {
                    float x = event.getX(0) - event.getX(1);
                    float y = event.getY(0) - event.getY(1);

                    scaleX = event.getX(0) + Math.abs(x / 2);
                    scaleY = event.getY(0) + Math.abs(y / 2);
                    minit = getXYLength(event);
                    scaleCount = lastScale;
                }
                break;
        }
    }


    public float getXYLength(MotionEvent event) {

        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);


        return (float) Math.sqrt(x * x + y * y);
    }

    private RectFModel isInRect(float x, float y, ArrayList<RectFModel> list) {

        int count = 0;
        for (RectFModel rectFModel : list) {
            if (rectFModel.rectF.contains(x, y)) {
                return rectFModel;
            }
            count++;
        }
        return null;
    }

    /**
     * 这是用来 获取raw下文件 String。
     * <p/>
     * 比如json。
     */
    public String readTextFileFromRawResourceId(Context context, int resourceId) {
        StringBuilder builder = new StringBuilder();

        BufferedReader reader = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(
                resourceId)));

        try {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                builder.append(line).append("\n");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return builder.toString();
    }




    public void setData() {

        int secondHasNextIndex = 0;
        int firstHasNextIndex = 0;

        ShareHolderDataModel shareHolderDataModel = new Gson().fromJson(readTextFileFromRawResourceId(mContext, R.raw.shareholder), ShareHolderDataModel.class);


        List<ShareHolderDataModel.ShareHolderItemModel> list = shareHolderDataModel.list;

        /**
         *   遍历数据  补全二级 三级 占位
         * */
        for (int i = 0; i < list.size(); i++) {
            ShareHolderDataModel.ShareHolderItemModel secondModel = list.get(i);
            if (secondModel.list == null || secondModel.list.size() == 0) {
                // 构建二级占位
                List<ShareHolderDataModel.ShareHolderItemModel> sList = new ArrayList<>();
                ShareHolderDataModel.ShareHolderItemModel sModel = new ShareHolderDataModel.ShareHolderItemModel();
                sModel.isPlaceholder = true;


                // 构建三级占位
                List<ShareHolderDataModel.ShareHolderItemModel> tList = new ArrayList<>();
                ShareHolderDataModel.ShareHolderItemModel tModel = new ShareHolderDataModel.ShareHolderItemModel();
                tModel.isPlaceholder = true;
                tList.add(tModel);
                sModel.list = tList;


                sList.add(sModel);
                secondModel.list = sList;

            } else {

                for (int j = 0; j < secondModel.list.size(); j++) {
                    ShareHolderDataModel.ShareHolderItemModel sModel = secondModel.list.get(j);
                    if (sModel.list == null || sModel.list.size() == 0) {
                        // 构建三级占位
                        List<ShareHolderDataModel.ShareHolderItemModel> tList = new ArrayList<>();
                        ShareHolderDataModel.ShareHolderItemModel tModel = new ShareHolderDataModel.ShareHolderItemModel();
                        tModel.isPlaceholder = true;
                        tList.add(tModel);
                        sModel.list = tList;

                    }
                }
            }
        }


        for (int i = 0; i < list.size(); i++) {
            ShareHolderDataModel.ShareHolderItemModel secondModel = list.get(i);
            if (secondModel != null && secondModel.list != null && secondModel.list.size() > 0) {
                RectFModel topRectModel = null;
                RectFModel bottomRectModel = null;

                for (int j = 0; j < secondModel.list.size(); j++) {
                    ShareHolderDataModel.ShareHolderItemModel thirdModel = secondModel.list.get(j);
                    if (thirdModel != null && thirdModel.list != null && thirdModel.list.size() > 0) {
                        RectFModel topRectModel1 = null;
                        RectFModel bottomRectModel1 = null;
                        for (int z = 0; z < thirdModel.list.size(); z++) {
                            RectFModel rectFModel = initPoint(thirdModel.list.get(z), thirdList, 3, 0xff03c769, 0xff01a766);
                            rectFModel.patherIndex = secondHasNextIndex;
                            if (z == 0) {
                                topRectModel1 = rectFModel;
                            }
                            if (z == thirdModel.list.size() - 1) {
                                bottomRectModel1 = rectFModel;
                            }
                            thirdList.add(rectFModel);
                        }
                        secondHasNextIndex++;
                        RectFModel r1 = initPointByPoint(secondModel.list.get(j), topRectModel1.rectF.top, bottomRectModel1.rectF.bottom, secondeList, 2, 0xff00cfe2, 0xff00ace0);
                        r1.patherIndex = i;
                        secondeList.add(r1);
                    }
//                    else {
//
////                        //占位
////                        RectFModel zaModel = initPoint1(null,thirdList,true,3,0xffffffff, 0xffffffff);
////                        zaModel.patherIndex = -1;
////                        thirdList.add(zaModel) ;
//
//                        RectFModel rectFModel = initPoint1(secondModel.list.get(j), thirdList, 2, 0xff00cfe2, 0xff00ace0);
//                        rectFModel.patherIndex = -1;
//                        secondeList.add(rectFModel);
//                        secondHasNextIndex++;
//                    }


                    if (j == 0) {
                        topRectModel = secondeList.get(secondeList.size() - 1);
                    }
                    if (j == secondModel.list.size() - 1) {
                        bottomRectModel = secondeList.get(secondeList.size() - 1);
                    }

                }
                RectFModel r2 = initPointByPoint(list.get(i), topRectModel.rectF.top, bottomRectModel.rectF.bottom, firstList, 1, 0xff40c1f8, 0xff0a8cf2);
                r2.patherIndex = 0;
                firstList.add(r2);
                firstHasNextIndex++;
            }

        }

        initCommonLines(secondeList, thirdList);
        initCommonLines(firstList, secondeList);



        initStartModule();

    }


    private RectFModel initPoint(ShareHolderDataModel.ShareHolderItemModel model, List<RectFModel> rectfList, int index, int startColor, int endColor) {

        RectF rect = new RectF();
        rect.left = intervalWidth * index + mItemWidth * (index - 1);
        if (model.isPlaceholder) {
            startColor = 0xffffffff;
            endColor = 0xffffffff;

        }

        if (rectfList.size() == 0) {
            rect.top = startTop + mItemHeight + intervalHeight;
        } else {
            rect.top = rectfList.get(rectfList.size() - 1).rectF.bottom + intervalHeight;
        }


        rect.right = rect.left + mItemWidth;
        rect.bottom = rect.top + mItemHeight;

        RectFModel rectFModel = new RectFModel();
        rectFModel.rectF = rect;
        if (model != null) {
            rectFModel.tag = model.name + "  " + rectfList.size();
        }

        PointF pointF = new PointF();
        pointF.x = rect.left + PhoneUtil.dip2px(mContext, 10);
        // 计算文字竖直方向起始坐标，使其可以居中
        Paint.FontMetricsInt fontMetrics = mRtextPaint.getFontMetricsInt();
        pointF.y = (rect.bottom + rect.top - fontMetrics.bottom - fontMetrics.top) / 2;

//
        rectFModel.textPoint = pointF;
        rectFModel.startColor = startColor;
        rectFModel.endColor = endColor;
        rectFModel.isPlaceholder = model.isPlaceholder;
        rectfBgList.add(rectFModel);


        return rectFModel;
    }


    private RectFModel initPointByPoint(ShareHolderDataModel.ShareHolderItemModel model, float byTop, float byBottom, List<RectFModel> rectFList, int index, int startColor, int endColor) {


        if (model.isPlaceholder) {
            startColor = 0xffffffff;
            endColor = 0xffffffff;

        }
        RectF rect = new RectF();
        rect.left = intervalWidth * index + mItemWidth * (index - 1);
        rect.right = rect.left + mItemWidth;


//        float top = byTop + (byBottom - byTop) / 2 - mItemHeight / 2;
        rect.top = byTop;
        rect.bottom = rect.top + mItemHeight;

        RectFModel rectFModel = new RectFModel();
        rectFModel.rectF = rect;
        rectFModel.tag = model.name + "  " + rectFList.size();

        PointF pointF = new PointF();
        pointF.x = rect.left + PhoneUtil.dip2px(mContext, 10);
        // 计算文字竖直方向起始坐标，使其可以居中
        Paint.FontMetricsInt fontMetrics = mRtextPaint.getFontMetricsInt();
        pointF.y = (rect.bottom + rect.top - fontMetrics.bottom - fontMetrics.top) / 2;

        rectFModel.textPoint = pointF;
        rectFModel.startColor = startColor;
        rectFModel.endColor = endColor;
        rectFModel.isPlaceholder = model.isPlaceholder;
        rectfBgList.add(rectFModel);


        return rectFModel;
    }

    private void initStartModule() {
        RectFModel rectFModel = new RectFModel();

        List<RectFModel> firstTopList = new ArrayList<>();// 起始item 终点坐标 以上
        List<RectFModel> firstBottomList = new ArrayList<>();// 起始item 终点坐标 以下

        if (firstList != null && firstList.size() > 0) {
            RectFModel startModel = firstList.get(0);
            RectFModel endModel = firstList.get(firstList.size() - 1);
            if (startModel.rectF != null && endModel.rectF != null) {
                RectF rect = new RectF();
                rect.right = startModel.rectF.left - intervalWidth;
                rect.left = rect.right - mItemHeight;
                rect.top = (endModel.rectF.bottom + startModel.rectF.top) / 2 + startModel.rectF.top - mItemWidth / 2;
                rect.bottom = rect.top + mItemWidth;

//                rect.right = rect.left + mItemWidth;

                rectFModel.rectF = rect;
                rectFModel.tag = "开始位置开始位置开始位置开始位置开始位置开始位置开始位置";
                rectFModel.isFirst = true;

                PointF pointF = new PointF();
                pointF.x = rect.left;
//                // 计算文字竖直方向起始坐标，使其可以居中
//                Paint.FontMetricsInt fontMetrics = mRtextPaint.getFontMetricsInt();
                pointF.y = rect.top + PhoneUtil.dip2px(mContext, 10);

                rectFModel.textPoint = pointF;
                rectFModel.startColor = 0xfffa7437;
                rectFModel.endColor = 0xfffeb02c;
                rectFModel.isPlaceholder = false;
                rectfBgList.add(rectFModel);


                scaleCount = 0.5f;
                mTran[0] = (-rect.left);
                mTran[1] = -((rect.bottom-rect.top)/2+rect.top-PhoneUtil.getDisplayHeight(mContext)/2);


            }

        }


        for (int i = 0; i < firstList.size(); i++) {
            RectFModel rf = firstList.get(i);

            if (rf != null && rf.rectF != null&&rectFModel.rectF!=null) {
                if (rf.rectF.top>=rectFModel.rectF.top+(rectFModel.rectF.bottom-rectFModel.rectF.top)/2){
                    firstTopList.add(rf);
                }else{
                    firstBottomList.add(0,rf);
                }
            }
        }
        initFirstLines(firstTopList,rectFModel,1);
        initFirstLines(firstBottomList,rectFModel,-1);

    }


    private void initCommonLines(List<RectFModel> oneList, List<RectFModel> twoList) {
        // 绘制线

        int samePosition = 0;

        int patherIndex = -1;
        for (int i = 0; i < twoList.size(); i++) {

            RectFModel rectFModel = twoList.get(i);
            if (rectFModel.patherIndex != -1 && !rectFModel.isPlaceholder) {
                RectF patherRect = oneList.get(rectFModel.patherIndex).rectF;


                RectF childRect = twoList.get(i).rectF;


                if (patherIndex == rectFModel.patherIndex) {
                    if (samePosition < 3)
                        samePosition++;
                } else {
                    patherIndex = rectFModel.patherIndex;
                    samePosition = 0;
                }


                // 绘制路径
                PointF fatherStartPointF = new PointF();
                fatherStartPointF.x = patherRect.right;
                fatherStartPointF.y = patherRect.top + (patherRect.bottom - patherRect.top) / 2 + PhoneUtil.dip2px(mContext, 5) * samePosition;


                PointF pathPointF1 = new PointF();
//                pathPointF1.x = fatherStartPointF.x + intervalWidth / 2;
//                pathPointF1.y = fatherStartPointF.y;


                pathPointF1.x = fatherStartPointF.x + intervalWidth / 6 * 5 - PhoneUtil.dip2px(mContext, 10) * samePosition;
//            }
                pathPointF1.y = fatherStartPointF.y;


                PointF pathPointF2 = new PointF();
                pathPointF2.x = pathPointF1.x;
                pathPointF2.y = childRect.top + (childRect.bottom - childRect.top) / 2;

                // 当前item left左侧 中心点坐标
                PointF childPointF = new PointF();
                childPointF.x = childRect.left;
                childPointF.y = childRect.top + (childRect.bottom - childRect.top) / 2;


                rectFModel.pathPointF1 = fatherStartPointF;
                rectFModel.pathPointF2 = pathPointF1;
                rectFModel.pathPointF3 = pathPointF2;
                rectFModel.pathPointF4 = childPointF;
            }
        }
    }

    private void initFirstLines(List<RectFModel> firstList, RectFModel rectFModel, int tag){

        // 绘制线
        for (int i = 0; i < firstList.size(); i++) {
            RectFModel fRectFModel = firstList.get(i);
            if (fRectFModel.patherIndex != -1 && !fRectFModel.isPlaceholder) {
                RectF patherRect = rectFModel.rectF;


                RectF childRect = firstList.get(i).rectF;

                // 绘制路径
                PointF fatherStartPointF = new PointF();
                fatherStartPointF.x = patherRect.right;

                int count = i+1;
                if(i>=4){
                    count=4;
                }
                fatherStartPointF.y = patherRect.top + (patherRect.bottom - patherRect.top) / 2+PhoneUtil.dip2px(mContext,10)*tag*count;


                PointF pathPointF1 = new PointF();
                pathPointF1.x = fatherStartPointF.x + intervalWidth / 6 * 5 - PhoneUtil.dip2px(mContext, 5) * count;
                pathPointF1.y = fatherStartPointF.y;

                PointF pathPointF2 = new PointF();
                pathPointF2.x = pathPointF1.x;
                pathPointF2.y = childRect.top + (childRect.bottom - childRect.top) / 2;

                // 当前item left左侧 中心点坐标
                PointF childPointF = new PointF();
                childPointF.x = childRect.left;
                childPointF.y = childRect.top + (childRect.bottom - childRect.top) / 2;


                fRectFModel.pathPointF1 = fatherStartPointF;
                fRectFModel.pathPointF2 = pathPointF1;
                fRectFModel.pathPointF3 = pathPointF2;
                fRectFModel.pathPointF4 = childPointF;
            }
        }
    }

}
