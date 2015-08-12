package com.qieyou.qieyoustore.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.widget.Toast;

import com.qieyou.qieyoustore.R;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMEvernoteHandler;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.controller.media.EvernoteShareContent;
import com.umeng.socialize.facebook.controller.UMFacebookHandler;
import com.umeng.socialize.facebook.controller.UMFacebookHandler.PostType;
import com.umeng.socialize.facebook.media.FaceBookShareContent;
import com.umeng.socialize.instagram.controller.UMInstagramHandler;
import com.umeng.socialize.instagram.media.InstagramShareContent;
import com.umeng.socialize.laiwang.controller.UMLWHandler;
import com.umeng.socialize.linkedin.controller.UMLinkedInHandler;
import com.umeng.socialize.linkedin.media.LinkedInShareContent;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.TencentWbShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.pinterest.controller.UMPinterestHandler;
import com.umeng.socialize.pinterest.media.PinterestShareContent;
import com.umeng.socialize.pocket.controller.UMPocketHandler;
import com.umeng.socialize.pocket.media.PocketShareContent;
import com.umeng.socialize.sso.EmailHandler;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.SmsHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;
import com.umeng.socialize.yixin.controller.UMYXHandler;
import com.umeng.socialize.ynote.controller.UMYNoteHandler;
import com.umeng.socialize.ynote.media.YNoteShareContent;

public class MyShareUtils  implements SocializeListeners.SnsPostListener{
	public static final String group_url ="http://m.totour.com/group/";
	public static final String forum_url ="http://m.totour.com/forum/";
	public static final String goods_url ="http://m.totour.com/item/";
	public static final String shop_url ="http://m.totour.com/special/inn?sid=";

	private UMSocialService mController;
	private Activity mActivity;
	private String title,content,targetUrl,imgUrl,musicUrl,videoUrl;
	private String forum_id;
	public MyShareUtils(Activity mActivity) {
		this.mActivity = mActivity;
		mController = UMServiceFactory.getUMSocialService("com.umeng.share");
	}

	/**
	 * 添加所有的平台</br>
	 */
	public void addCustomPlatforms() {
		try{
			// 添加微信平台
			addWXPlatform();
			// 添加QQ平台
//			addQQQZonePlatform();
			// 添加印象笔记平台
//		addEverNote();
			// 添加facebook平台
//			addFacebook();
			// 添加Instagram平台
//		addInstagram();
			// 添加来往、来往动态平台
//		addLaiWang();
			// 添加LinkedIn平台
//		addLinkedIn();
			// 添加Pinterest平台
//		addPinterest();
			// 添加Pocket平台
//		addPocket();
			// 添加有道云平台
//		addYNote();
			// 添加易信平台
//		addYXPlatform();
			// 添加短信平台
//		addSMS();
			// 添加email平台
//		addEmail();

		/*mController.getConfig().setPlatforms(SHARE_MEDIA.WEIXIN,
				SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE,
				SHARE_MEDIA.SINA, SHARE_MEDIA.TENCENT, SHARE_MEDIA.DOUBAN,
				SHARE_MEDIA.RENREN, SHARE_MEDIA.EMAIL, SHARE_MEDIA.EVERNOTE,
				SHARE_MEDIA.FACEBOOK, SHARE_MEDIA.GOOGLEPLUS,
				SHARE_MEDIA.INSTAGRAM, SHARE_MEDIA.LAIWANG,
				SHARE_MEDIA.LAIWANG_DYNAMIC, SHARE_MEDIA.LINKEDIN,
				SHARE_MEDIA.PINTEREST, SHARE_MEDIA.POCKET, SHARE_MEDIA.SMS,
				SHARE_MEDIA.TWITTER, SHARE_MEDIA.YIXIN,
				SHARE_MEDIA.YIXIN_CIRCLE, SHARE_MEDIA.YNOTE);*/
			mController.getConfig().setPlatforms(
					SHARE_MEDIA.WEIXIN_CIRCLE,
					SHARE_MEDIA.WEIXIN,
					SHARE_MEDIA.QQ,
					SHARE_MEDIA.SINA,
					SHARE_MEDIA.QZONE/*,

				SHARE_MEDIA.TENCENT,
				SHARE_MEDIA.DOUBAN,
				SHARE_MEDIA.RENREN,
				SHARE_MEDIA.EMAIL,
				SHARE_MEDIA.EVERNOTE,
				SHARE_MEDIA.FACEBOOK,
				SHARE_MEDIA.GOOGLEPLUS,
				SHARE_MEDIA.INSTAGRAM,
				SHARE_MEDIA.LAIWANG,
				SHARE_MEDIA.LAIWANG_DYNAMIC,
				SHARE_MEDIA.LINKEDIN,
				SHARE_MEDIA.PINTEREST,
				SHARE_MEDIA.POCKET,
				SHARE_MEDIA.SMS,
				SHARE_MEDIA.TWITTER,
				SHARE_MEDIA.YIXIN,
				SHARE_MEDIA.YIXIN_CIRCLE,
				SHARE_MEDIA.YNOTE*/);
			mController.openShare(mActivity, this);
		}catch(NullPointerException e){

		}
	}

	/**
	 * 添加短信平台</br>
	 */
	private void addSMS()  throws NullPointerException{
		// 添加短信
		SmsHandler smsHandler = new SmsHandler();
		smsHandler.addToSocialSDK();
	}

	/**
	 * 添加Email平台</br>
	 */
	private void addEmail() throws NullPointerException {
		// 添加email
		EmailHandler emailHandler = new EmailHandler();
		emailHandler.addToSocialSDK();
	}

	/**
	 * Pocket分享。pockect只支持分享网络链接</br>
	 */
	private void addPocket() {
		UMPocketHandler pocketHandler = new UMPocketHandler(mActivity);
		pocketHandler.addToSocialSDK();
		PocketShareContent pocketShareContent = new PocketShareContent();
		pocketShareContent.setShareContent(content);
		mController.setShareMedia(pocketShareContent);
	}

	/**
	 * LinkedIn分享。LinkedIn只支持图片，文本，图文分享</br>
	 */
	private void addLinkedIn() throws NullPointerException {
		UMLinkedInHandler linkedInHandler = new UMLinkedInHandler(mActivity);
		linkedInHandler.addToSocialSDK();
		LinkedInShareContent linkedInShareContent = new LinkedInShareContent();
		linkedInShareContent.setShareContent(content);
		mController.setShareMedia(linkedInShareContent);
	}

	/**
	 * 有道云笔记分享。有道云笔记只支持图片，文本，图文分享</br>
	 */
	private void addYNote()  throws NullPointerException{
		UMYNoteHandler yNoteHandler = new UMYNoteHandler(mActivity);
		yNoteHandler.addToSocialSDK();
		YNoteShareContent yNoteShareContent = new YNoteShareContent();
		yNoteShareContent.setShareContent(content);
		yNoteShareContent.setTitle(title);
		UMImage mUMImgBitmap = new UMImage(mActivity, imgUrl);
		yNoteShareContent.setShareImage(mUMImgBitmap);
		mController.setShareMedia(yNoteShareContent);
	}

	/**
	 * 添加印象笔记平台
	 */
	private void addEverNote()  throws NullPointerException{
		UMEvernoteHandler evernoteHandler = new UMEvernoteHandler(
				mActivity);
		evernoteHandler.addToSocialSDK();

		// 设置evernote的分享内容
		EvernoteShareContent shareContent = new EvernoteShareContent(content);
		shareContent.setShareMedia(new UMImage(mActivity,
				R.drawable.ic_launcher));
		mController.setShareMedia(shareContent);
	}

	/**
	 * 添加Pinterest平台
	 */
	private void addPinterest()  throws NullPointerException{
		/**
		 * app id需到pinterest开发网站( https://developers.pinterest.com/ )自行申请.
		 */
		UMPinterestHandler pinterestHandler = new UMPinterestHandler(
				mActivity, "1439206");
		pinterestHandler.addToSocialSDK();

		// 设置Pinterest的分享内容
		PinterestShareContent shareContent = new PinterestShareContent(content);
		shareContent.setShareMedia(new UMImage(mActivity,
				R.drawable.ic_launcher));
		mController.setShareMedia(shareContent);
	}

	/**
	 * 添加来往和来往动态平台</br>
	 */
	private void addLaiWang()  throws NullPointerException{

		String appToken = "laiwangd497e70d4";
		String secretID = "d497e70d4c3e4efeab1381476bac4c5e";
		// laiwangd497e70d4:来往appToken,d497e70d4c3e4efeab1381476bac4c5e:来往secretID
		// 添加来往的支持
		UMLWHandler umlwHandler = new UMLWHandler(mActivity, appToken,
				secretID);
		umlwHandler.addToSocialSDK();

		// 添加来往动态的支持
		UMLWHandler lwDynamicHandler = new UMLWHandler(mActivity,
				appToken, secretID);
		lwDynamicHandler.setToCircle(true);
		lwDynamicHandler.addToSocialSDK();
	}

	/**
	 * </br> Instagram只支持图片分享, 只支持纯图片分享.</br>
	 */
	private void addInstagram()  throws NullPointerException{
		// 构建Instagram的Handler
		UMInstagramHandler instagramHandler = new UMInstagramHandler(mActivity);
		instagramHandler.addToSocialSDK();

		UMImage localImage = new UMImage(mActivity, R.drawable.ic_launcher);

		// // 添加分享到Instagram的内容
		InstagramShareContent instagramShareContent = new InstagramShareContent(
				localImage);
		mController.setShareMedia(instagramShareContent);
	}

	/**
	 * @功能描述 : 添加微信平台分享
	 * @return
	 */
	private void addWXPlatform()  throws NullPointerException{
		// 注意：在微信授权的时候，必须传递appSecret
		// wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
//		AppID：wxd093651bec10ad64
//		AppSecret：6622c38da702e36ae4247ae187fce28e

		String appId = "wxb4c1387e3794017d";
		String appSecret = "0c1baa0c5086eefe182bb874fa3dfb7e";
		// 添加微信平台
		UMWXHandler wxHandler = new UMWXHandler(mActivity, appId,appSecret);
		wxHandler.addToSocialSDK();

		// 支持微信朋友圈
		UMWXHandler wxCircleHandler = new UMWXHandler(mActivity,appId, appSecret);
		wxCircleHandler.setToCircle(true);
		wxCircleHandler.addToSocialSDK();
	}

	/**
	 * @功能描述 : 添加QQ平台支持 QQ分享的内容， 包含四种类型， 即单纯的文字、图片、音乐、视频. 参数说明 : title, summary,
	 *       image url中必须至少设置一个, targetUrl必须设置,网页地址必须以"http://"开头 . title :
	 *       要分享标题 summary : 要分享的文字概述 image url : 图片地址 [以上三个参数至少填写一个] targetUrl
	 *       : 用户点击该分享时跳转到的目标地址 [必填] ( 若不填写则默认设置为友盟主页 )
	 * @return
	 */
	private void addQQQZonePlatform()  throws NullPointerException{
		String appId = "1104012075";
		String appKey = "QnZGX9s0JTbbtgRW";
		// 添加QQ支持, 并且设置QQ分享内容的target url
		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(mActivity,appId, appKey);
		qqSsoHandler.setTargetUrl(targetUrl);
		qqSsoHandler.addToSocialSDK();

		// 添加QZone平台
		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(mActivity, appId, appKey);
		qZoneSsoHandler.addToSocialSDK();
	}

	/**
	 * @Title: addYXPlatform
	 * @Description:
	 * @throws
	 */
	private void addYXPlatform()  throws NullPointerException{
		// 添加易信平台
		UMYXHandler yixinHandler = new UMYXHandler(mActivity,"yxc0614e80c9304c11b0391514d09f13bf");
		// 关闭分享时的等待Dialog
		yixinHandler.enableLoadingDialog(false);
		// 设置target Url, 必须以http或者https开头
		yixinHandler.setTargetUrl(targetUrl);
		yixinHandler.addToSocialSDK();

		// 易信朋友圈平台
		UMYXHandler yxCircleHandler = new UMYXHandler(mActivity,"yxc0614e80c9304c11b0391514d09f13bf");
		yxCircleHandler.setToCircle(true);
		yxCircleHandler.addToSocialSDK();

	}

	/**
	 * @Title: addFacebook
	 * @Description:
	 * @throws
	 */
	private void addFacebook() throws NullPointerException{
		UMImage mUMImgBitmap = new UMImage(mActivity, R.drawable.ic_launcher);
		UMFacebookHandler mFacebookHandler = new UMFacebookHandler(mActivity, "567261760019884", PostType.FEED);
		mFacebookHandler.addToSocialSDK();

		FaceBookShareContent fbContent = new FaceBookShareContent(content);
		fbContent.setShareImage(mUMImgBitmap);
		fbContent.setShareContent(content + new Date().toString());
		fbContent.setTitle(title);
		fbContent.setCaption("Caption - Fb");
		fbContent.setDescription("独立拆分测试");
		fbContent.setTargetUrl(targetUrl);
		mController.setShareMedia(fbContent);

		mController.setShareContent(content);
		mController.setShareMedia(mUMImgBitmap);
	}

	/**
	 * 根据不同的平台设置不同的分享内容</br>
	 */
	public void setShareContent(String title,String _content,String targetUrl,String imgUrl,String musicUrl,String videoUrl, String forum_id) {
		setShareContent(title, _content, targetUrl,imgUrl,musicUrl, videoUrl);
		this.forum_id = forum_id;
	}

	/**
	 * 根据不同的平台设置不同的分享内容</br>
	 */
	public void setShareContent(String title,String _content,String targetUrl,int imgUrl,String musicUrl,String videoUrl, String forum_id) {
		setShareContent(title, _content, targetUrl,imgUrl,musicUrl, videoUrl);
		this.forum_id = forum_id;
	}

	/**
	 * 根据不同的平台设置不同的分享内容</br>
	 */
	public void setShareContent(String title,String _content,String targetUrl,String imgUrl,String musicUrl,String videoUrl) {
		try{
			this.title = title;
			this.content = (null == _content||"".equals(_content))?"且游旅行":_content;
			this.targetUrl = targetUrl;
			this.imgUrl = imgUrl;
			this.musicUrl = musicUrl;
			this.videoUrl = videoUrl;
			this.forum_id = "";
			// 配置SSO
			mController.getConfig().setSsoHandler(new SinaSsoHandler());

			mController.getConfig().setSsoHandler(new TencentWBSsoHandler());

			QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(mActivity, "1101783282",
					"c7394704798a158208a74ab60104f0ba");
			qZoneSsoHandler.addToSocialSDK();
			UMQQSsoHandler umqqSsoHandler = new UMQQSsoHandler(mActivity,
					"1101783282",
					"c7394704798a158208a74ab60104f0ba");
			umqqSsoHandler.addToSocialSDK();
			mController.setShareContent(this.content);



			UMImage localImage = new UMImage(mActivity, R.drawable.ic_launcher);
			UMImage urlImage = null;
			if("".equals(imgUrl)){
				urlImage = localImage;
			} else {
				urlImage = new UMImage(mActivity,imgUrl);
			}


			// UMImage resImage = new UMImage(mActivity, R.drawable.icon);

			WeiXinShareContent weixinContent = new WeiXinShareContent();
			weixinContent.setShareContent(this.content);
			weixinContent.setTitle(title);
			weixinContent.setTargetUrl(targetUrl);
//		weixinContent.setShareMedia(urlImage);
			weixinContent.setShareImage(urlImage);
			mController.setShareMedia(weixinContent);
//		mController.set

			// 设置朋友圈分享的内容
			CircleShareContent circleMedia = new CircleShareContent();
			circleMedia.setShareContent(this.content);
			circleMedia.setTitle(title);
			circleMedia.setShareImage(urlImage);
			// circleMedia.setShareMedia(uMusic);
			// circleMedia.setShareMedia(video);
			circleMedia.setTargetUrl(targetUrl);
			mController.setShareMedia(circleMedia);

			//设置新浪分享
			SinaShareContent sinaContent = new SinaShareContent(urlImage);
			sinaContent.setShareContent(title+"\n"+targetUrl);
			sinaContent.setTargetUrl(targetUrl);
			sinaContent.setShareImage(urlImage);
			mController.setShareMedia(sinaContent);

//			UMImage qzoneImage = new UMImage(mActivity,imgUrl);
//			qzoneImage.setTargetUrl(imgUrl);

			// 设置QQ空间分享内容
			QZoneShareContent qzone = new QZoneShareContent();
			qzone.setShareContent(content);
			qzone.setTargetUrl(targetUrl);
			qzone.setTitle(title);
			qzone.setShareImage(urlImage);
			mController.setShareMedia(qzone);

			QQShareContent qqShareContent = new QQShareContent();
			qqShareContent.setShareContent(content);
			qqShareContent.setTitle(title);
			qqShareContent.setShareImage(urlImage);
			// qqShareContent.setShareMusic(uMusic);
			// qqShareContent.setShareVideo(video);
			qqShareContent.setTargetUrl(targetUrl);
			mController.setShareMedia(qqShareContent);

			TencentWbShareContent tencent = new TencentWbShareContent();
			tencent.setShareContent(content);
			// 设置tencent分享内容
			mController.setShareMedia(tencent);

			// 视频分享
		/*
		 *
		 *

		// APP ID：201874, API
		// * KEY：28401c0964f04a72a14c812d6132fcef, Secret
		// * Key：3bf66e42db1e4fa9829b955cc300b737.
		RenrenSsoHandler renrenSsoHandler = new RenrenSsoHandler(
				mActivity, "201874",
				"28401c0964f04a72a14c812d6132fcef",
				"3bf66e42db1e4fa9829b955cc300b737");
		mController.getConfig().setSsoHandler(renrenSsoHandler);

		// 视频分享
		UMVideo video = new UMVideo(videoUrl);
		// vedio.setThumb("http://www.haowai.info/images/pic/home/social/img-1.png");
		video.setTitle(title);
		video.setThumb(urlImage);

		UMusic uMusic = new UMusic(musicUrl);
		uMusic.setAuthor("号外");
		uMusic.setTitle(title);
		uMusic.setThumb(urlImage);
		// uMusic.setThumb("http://www.haowai.info/images/pic/social/chart_1.png");
		video.setThumb(new UMImage(mActivity, BitmapFactory
				.decodeResource(mActivity.getResources(), R.drawable.ic_launcher)));

		// 设置renren分享内容
		RenrenShareContent renrenShareContent = new RenrenShareContent();
		renrenShareContent.setShareContent(content);
		UMImage image = new UMImage(mActivity,
				BitmapFactory.decodeResource(mActivity.getResources(), R.drawable.ic_launcher));
		image.setTitle(title);
		image.setThumb(imgUrl);
		renrenShareContent.setShareImage(image);
		renrenShareContent.setAppWebSite("http://www.haowai.info");
		mController.setShareMedia(renrenShareContent);

		UMVideo umVideo = new UMVideo(videoUrl);
		umVideo.setThumb(imgUrl);
		umVideo.setTitle(title);


		// 设置邮件分享内容， 如果需要分享图片则只支持本地图片
		MailShareContent mail = new MailShareContent(localImage);
		mail.setTitle(title);
		mail.setShareContent(content);
		// 设置tencent分享内容
		mController.setShareMedia(mail);

		// 设置短信分享内容
		SmsShareContent sms = new SmsShareContent();
		sms.setShareContent(content);
		sms.setShareImage(urlImage);
		mController.setShareMedia(sms);

		TwitterShareContent twitterShareContent = new TwitterShareContent();
		twitterShareContent.setShareContent(content);
		twitterShareContent.setShareMedia(localImage);
		mController.setShareMedia(twitterShareContent);

		GooglePlusShareContent googlePlusShareContent = new GooglePlusShareContent();
		googlePlusShareContent.setShareContent(content);
		googlePlusShareContent.setShareMedia(localImage);
		mController.setShareMedia(googlePlusShareContent);

		// 来往分享内容
		LWShareContent lwShareContent = new LWShareContent();
		// lwShareContent.setShareImage(urlImage);
		// lwShareContent.setShareMedia(uMusic);
		lwShareContent.setShareMedia(umVideo);
		lwShareContent.setTitle(title);
		lwShareContent.setMessageFrom("来自号外");
		lwShareContent.setShareContent(content);
		mController.setShareMedia(lwShareContent);

		// 来往动态分享内容
		LWDynamicShareContent lwDynamicShareContent = new LWDynamicShareContent();
		// lwDynamicShareContent.setShareImage(urlImage);
		// lwDynamicShareContent.setShareMedia(uMusic);
		lwDynamicShareContent.setShareMedia(umVideo);
		lwDynamicShareContent.setTitle(title);
		lwDynamicShareContent.setMessageFrom("来自号外");
		lwDynamicShareContent.setShareContent(content);
		lwDynamicShareContent.setTargetUrl(targetUrl);
		mController.setShareMedia(lwDynamicShareContent);*/
		}catch(NullPointerException e){

		}



	}

	/**
	 * 根据不同的平台设置不同的分享内容</br>
	 */
	public void setShareContent(String title,String _content,String targetUrl,int imgRes,String musicUrl,String videoUrl) {
		try{
			this.title = title;
			this.content = (null == _content||"".equals(_content))?"且游旅行":_content;
			this.targetUrl = targetUrl;
			this.musicUrl = musicUrl;
			this.videoUrl = videoUrl;
			this.forum_id = "";
			// 配置SSO
			mController.getConfig().setSsoHandler(new SinaSsoHandler());

			mController.getConfig().setSsoHandler(new TencentWBSsoHandler());

			QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(mActivity, "1101783282",
					"c7394704798a158208a74ab60104f0ba");
			qZoneSsoHandler.addToSocialSDK();

			UMQQSsoHandler umqqSsoHandler = new UMQQSsoHandler(mActivity,
					"1101783282",
					"c7394704798a158208a74ab60104f0ba");
			umqqSsoHandler.addToSocialSDK();
			mController.setShareContent(this.content);



			UMImage urlImage = new UMImage(mActivity,imgRes);


			// UMImage resImage = new UMImage(mActivity, R.drawable.icon);

			WeiXinShareContent weixinContent = new WeiXinShareContent();
			weixinContent.setShareContent(this.content);
			weixinContent.setTitle(title);
			weixinContent.setTargetUrl(targetUrl);
//		weixinContent.setShareMedia(urlImage);
			weixinContent.setShareImage(urlImage);
			mController.setShareMedia(weixinContent);
//		mController.set

			// 设置朋友圈分享的内容
			CircleShareContent circleMedia = new CircleShareContent();
			circleMedia.setShareContent(this.content);
			circleMedia.setTitle(title);
			circleMedia.setShareImage(urlImage);
			// circleMedia.setShareMedia(uMusic);
			// circleMedia.setShareMedia(video);
			circleMedia.setTargetUrl(targetUrl);
			mController.setShareMedia(circleMedia);

			//设置新浪分享
			SinaShareContent sinaContent = new SinaShareContent(urlImage);
			sinaContent.setShareContent(title+"\n"+targetUrl);
			sinaContent.setTargetUrl(targetUrl);
			sinaContent.setShareImage(urlImage);
			mController.setShareMedia(sinaContent);

			UMImage qzoneImage = new UMImage(mActivity,imgUrl);
			qzoneImage.setTargetUrl(imgUrl);

			// 设置QQ空间分享内容
			QZoneShareContent qzone = new QZoneShareContent();
			qzone.setShareContent(content);
			qzone.setTargetUrl(targetUrl);
			qzone.setTitle(title);
			qzone.setShareImage(urlImage);
			mController.setShareMedia(qzone);

			QQShareContent qqShareContent = new QQShareContent();
			qqShareContent.setShareContent(content);
			qqShareContent.setTitle(title);
			qqShareContent.setShareImage(urlImage);
			// qqShareContent.setShareMusic(uMusic);
			// qqShareContent.setShareVideo(video);
			qqShareContent.setTargetUrl(targetUrl);
			mController.setShareMedia(qqShareContent);

			TencentWbShareContent tencent = new TencentWbShareContent();
			tencent.setShareContent(content);
			// 设置tencent分享内容
			mController.setShareMedia(tencent);

			// 视频分享
		/*
		 *
		 *

		// APP ID：201874, API
		// * KEY：28401c0964f04a72a14c812d6132fcef, Secret
		// * Key：3bf66e42db1e4fa9829b955cc300b737.
		RenrenSsoHandler renrenSsoHandler = new RenrenSsoHandler(
				mActivity, "201874",
				"28401c0964f04a72a14c812d6132fcef",
				"3bf66e42db1e4fa9829b955cc300b737");
		mController.getConfig().setSsoHandler(renrenSsoHandler);

		// 视频分享
		UMVideo video = new UMVideo(videoUrl);
		// vedio.setThumb("http://www.haowai.info/images/pic/home/social/img-1.png");
		video.setTitle(title);
		video.setThumb(urlImage);

		UMusic uMusic = new UMusic(musicUrl);
		uMusic.setAuthor("号外");
		uMusic.setTitle(title);
		uMusic.setThumb(urlImage);
		// uMusic.setThumb("http://www.haowai.info/images/pic/social/chart_1.png");
		video.setThumb(new UMImage(mActivity, BitmapFactory
				.decodeResource(mActivity.getResources(), R.drawable.ic_launcher)));

		// 设置renren分享内容
		RenrenShareContent renrenShareContent = new RenrenShareContent();
		renrenShareContent.setShareContent(content);
		UMImage image = new UMImage(mActivity,
				BitmapFactory.decodeResource(mActivity.getResources(), R.drawable.ic_launcher));
		image.setTitle(title);
		image.setThumb(imgUrl);
		renrenShareContent.setShareImage(image);
		renrenShareContent.setAppWebSite("http://www.haowai.info");
		mController.setShareMedia(renrenShareContent);

		UMVideo umVideo = new UMVideo(videoUrl);
		umVideo.setThumb(imgUrl);
		umVideo.setTitle(title);


		// 设置邮件分享内容， 如果需要分享图片则只支持本地图片
		MailShareContent mail = new MailShareContent(localImage);
		mail.setTitle(title);
		mail.setShareContent(content);
		// 设置tencent分享内容
		mController.setShareMedia(mail);

		// 设置短信分享内容
		SmsShareContent sms = new SmsShareContent();
		sms.setShareContent(content);
		sms.setShareImage(urlImage);
		mController.setShareMedia(sms);

		TwitterShareContent twitterShareContent = new TwitterShareContent();
		twitterShareContent.setShareContent(content);
		twitterShareContent.setShareMedia(localImage);
		mController.setShareMedia(twitterShareContent);

		GooglePlusShareContent googlePlusShareContent = new GooglePlusShareContent();
		googlePlusShareContent.setShareContent(content);
		googlePlusShareContent.setShareMedia(localImage);
		mController.setShareMedia(googlePlusShareContent);

		// 来往分享内容
		LWShareContent lwShareContent = new LWShareContent();
		// lwShareContent.setShareImage(urlImage);
		// lwShareContent.setShareMedia(uMusic);
		lwShareContent.setShareMedia(umVideo);
		lwShareContent.setTitle(title);
		lwShareContent.setMessageFrom("来自号外");
		lwShareContent.setShareContent(content);
		mController.setShareMedia(lwShareContent);

		// 来往动态分享内容
		LWDynamicShareContent lwDynamicShareContent = new LWDynamicShareContent();
		// lwDynamicShareContent.setShareImage(urlImage);
		// lwDynamicShareContent.setShareMedia(uMusic);
		lwDynamicShareContent.setShareMedia(umVideo);
		lwDynamicShareContent.setTitle(title);
		lwDynamicShareContent.setMessageFrom("来自号外");
		lwDynamicShareContent.setShareContent(content);
		lwDynamicShareContent.setTargetUrl(targetUrl);
		mController.setShareMedia(lwDynamicShareContent);*/
		}catch(NullPointerException e){

		}



	}

	public void onStart(){

	}

	public void onComplete(SHARE_MEDIA share_media, int i, SocializeEntity socializeEntity) {
		if (200 == i) {
//			Toast.makeText(MyApplication.getInstance(), "分享成功", Toast.LENGTH_SHORT).show();
//			DebugLog.systemOut("分享成功");
//			if("".equals(forum_id)){
//				return;
//			}
//			Map<String, String> params = new HashMap<String, String>();
//			params.put("forum", forum_id);
//
//			RequestManager.getInstance().doPost(RequestURL.getInstance().URL_FORUM_SHARED, new Response.Listener<String>() {
//
//				@Override
//				public void onResponse(String returnStr) {
//					// TODO Auto-generated method stub
//					DebugLog.systemOut("returnStr=" + returnStr);
//				}
//			}, new Response.ErrorListener() {
//
//				@Override
//				public void onErrorResponse(VolleyError error) {
//					// TODO Auto-generated method stub
//				}
//			}, params);
//
//		}else{
//			Toast.makeText(MyApplication.getInstance(),"分享失败",Toast.LENGTH_SHORT).show();
//			DebugLog.systemOut("分享失败");
//		}
		}
	}
}
