package com.allen.douban.serviceimpl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import com.allen.douban.bean.ArticleDetailBean;
import com.allen.douban.bean.ArticleEditBean;
import com.allen.douban.bean.ArticlePreviewBean;
import com.allen.douban.bean.DiscoveryArticleBean;
import com.allen.douban.bean.MyArticleBean;
import com.allen.douban.bean.PageBean;
import com.allen.douban.dao.*;
import com.allen.douban.entity.Article;
import com.allen.douban.entity.Msg;
import com.allen.douban.entity.Type;
import com.allen.douban.factory.DaoFactroy;
import com.allen.douban.service.ArticleService;
import com.allen.douban.util.DbUtil;
import com.allen.douban.util.ImageIOUtil;

public class ArticleServiceImpl implements ArticleService {

	private ArticleDao articleDao = (ArticleDao) DaoFactroy.getInstance().getDao("ArticleDao");
	private TypeDao typeDao = (TypeDao) DaoFactroy.getInstance().getDao("TypeDao");

	@Override
	public ArticleEditBean findArticleEdit(int articleId) {
		Article article = articleDao.queryArticleById(articleId);
		List<Type> typeList = typeDao.queryAllType();
		if (article == null || typeList == null) {
			return null;
		}
		ArticleEditBean aticleEditBean = new ArticleEditBean(articleId, article.getArticleTitle(),
				article.getArticleText(), typeList);
		return aticleEditBean;
	}

	@Override
	public Msg findArticleDetail(int articleId, int userId) {
		Article article = articleDao.queryArticleById(articleId);
		CollectDao collectDao = new CollectDao();
		LikeDao likeDao = new LikeDao();
		ForwardDao forwardDao = new ForwardDao();
		
		if (article == null) {
			return new Msg(false,"文章不存在！", null);
		}
		
		List<Type> types = typeDao.queryTypeByArticleId(articleId);
		
		String nickname = new UserServiceImpl().findUserById(article.getUserId()).getNickname();
		int collectCount = collectDao.getArticleCollectCount(articleId);
		int forwardCount = forwardDao.getForwardCount(articleId);
		
		boolean isLiked = likeDao.isLiked(articleId, userId, 1);
		boolean isForward = forwardDao.isForwarded(userId, articleId);
		boolean isCollected = collectDao.isCollected(articleId, userId);
		
		ArticleDetailBean articleDetailBean = new ArticleDetailBean(article, forwardCount, collectCount, types,
				nickname, isLiked, isForward, isCollected);
		return new Msg(true,"成功", articleDetailBean);
	}

	@Override
	public Msg findFriendArticle(int userId, PageBean pageBean) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> articles = articleDao.queryFriArticleByUserId(userId, pageBean);
		if (articles == null) {
			return new Msg(false,"没有关注", null);
		}
		List<ArticlePreviewBean> datas = new ArrayList<>();
		for (Map<String, Object> map : articles) {
			/**
			 * 去掉富文本中所有HTML标签，并且截取前120个字符，作为文章预览
			 */
			String HTMLtext = (String) map.get("articleText");
			String text = HTMLtext.replaceAll("<\\/?[^>]*>", "").replaceAll("\\s*", "");
			String subText = text;
			if (text.length() > 120) {
				subText = text.substring(0, 120);
				subText += "...";
			}
			// 赋值bean
			int friendId = (int) map.get("userId");
			String nickname = (String) map.get("nickname");
			String title = (String) map.get("articleTitle");
			int articleId = (int) map.get("articleId");
			Date createdTime = (Date) map.get("createdTime");
			int authorId = (int) map.get("authorId");
			/**
			 * 如果好友ID等于作者ID 说明是好友发表文章，反之是好友转发文章
			 */
			int type = (friendId == authorId) ? 1 : 2;
			String authorName = null;
			if (type == 2) {
				authorName = articleDao.queryAuthorByArticleId((Integer) map.get("articleId"));
			} else {
				authorName = nickname;
			}
			ArticlePreviewBean articlePreview = new ArticlePreviewBean(friendId, nickname, articleId, authorName, title,
					subText, createdTime, type);
			datas.add(articlePreview);
		}
		return new Msg(true,"查询成功", datas);
	}

	@Override
	public List<DiscoveryArticleBean> findArticleByType(String typeIdStr, PageBean pageBean) {
		int typeId;
		try {
			typeId = Integer.parseInt(typeIdStr);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		List<Map<String, Object>> list = articleDao.queryArticleByTypeId(typeId, pageBean);
		List<DiscoveryArticleBean> result = new ArrayList<>();
		if (list == null || list.size() == 0) {
			return null;
		}
		for (Map<String, Object> map : list) {
			int authorId = (int) map.get("userId");
			int articleId = (int) map.get("articleId");
			String authorName = (String) map.get("nickname");
			String title = (String) map.get("articleTitle");
			Date createdTime = (Date) map.get("createdTime");
			/**
			 * 去掉富文本中所有HTML标签，并且截取前120个字符，作为文章预览
			 */
			String HTMLtext = (String) map.get("articleText");
			String text = HTMLtext.replaceAll("<\\/?[^>]*>", "").replaceAll("\\s*", "");
			String subText = text;
			if (text.length() > 120) {
				subText = text.substring(0, 120);
				subText += "...";
			}
			int likeCount = new LikeDao().getLikeCount(articleId, 1);
			int forwardCount = new ForwardDao().getForwardCount(articleId);
			int collectCount = new CollectDao().getArticleCollectCount(articleId);
			DiscoveryArticleBean bean = new DiscoveryArticleBean(authorId, articleId, authorName, title, subText,
					createdTime, likeCount, forwardCount, collectCount);
			result.add(bean);
		}
		return result;

	}

	@Override
	public List<DiscoveryArticleBean> findHitArticle(PageBean pageBean) {
		List<Map<String, Object>> list = articleDao.queryHitArticle(pageBean);
		List<DiscoveryArticleBean> result = new ArrayList<>();
		if (list == null || list.size() == 0) {
			return null;
		}
		for (Map<String, Object> map : list) {
			int authorId = (int) map.get("userId");
			int articleId = (int) map.get("articleId");
			String authorName = (String) map.get("nickname");
			String title = (String) map.get("articleTitle");
			Date createdTime = (Date) map.get("createdTime");
			/**
			 * 去掉富文本中所有HTML标签，并且截取前120个字符，作为文章预览
			 */
			String HTMLtext = (String) map.get("articleText");
			String text = HTMLtext.replaceAll("<\\/?[^>]*>", "").replaceAll("\\s*", "");
			String subText = text;
			if (text.length() > 120) {
				subText = text.substring(0, 120);
				subText += "...";
			}
			int likeCount = new LikeDao().getLikeCount(articleId, 1);
			int forwardCount = new ForwardDao().getForwardCount(articleId);
			int collectCount = new CollectDao().getArticleCollectCount(articleId);
			DiscoveryArticleBean bean = new DiscoveryArticleBean(authorId, articleId, authorName, title, subText,
					createdTime, likeCount, forwardCount, collectCount);
			result.add(bean);
		}
		return result;
	}

	@Override
	public List<MyArticleBean> findMyArticle(int userId, PageBean pageBean) {
		List<Article> articles = articleDao.queryArticleByUserId(userId, pageBean);
		if (articles == null || articles.size() == 0) {
			return null;
		}
		List<MyArticleBean> resultList = new ArrayList<>();
		for (Article a : articles) {
			resultList.add(new MyArticleBean(a));
		}
		return resultList;
	}

	@Override
	public Msg addNewArticle(int userId, String title, String text, List<String> typeIdStr) {
		int newId = articleDao.getNextId();
		int[] typeId = new int[typeIdStr.size()];
		for (int i = 0; i < typeIdStr.size(); i++) {
			typeId[i] = Integer.parseInt(typeIdStr.get(i));
		}
		Article article = new Article();
		article.setArticleTitle(title);
		article.setArticleText(text);
		article.setArticleLikes(0);
		article.setUserId(userId);
		article.setCreatedTime(new java.sql.Date(new Date().getTime()));
		article.setUpdateTime(new java.sql.Date(new Date().getTime()));

		// 开始事务
		DbUtil.beginTransaction();
		boolean isSuccess = false;
		try {
			articleDao.addArticle(article);
			typeDao.addArticleTypeMapping(typeId, newId);
			isSuccess = true;
		} catch (Exception e) {
			DbUtil.rollbackTransaction();
		}
		if (isSuccess) {
			DbUtil.commitTransaction();
			return new Msg(true,"新增文章成功");
		} else {
			return new Msg(false,"新增文章失败");
		}
	}

	@Override
	public Msg editArticle(String articleIdStr, String title, String text, List<String> typeIdStr) {
		int[] typeId = new int[typeIdStr.size()];
		int articleId = 0;
		try {
			for (int i = 0; i < typeIdStr.size(); i++) {
				typeId[i] = Integer.parseInt(typeIdStr.get(i));
			}
			articleId = Integer.parseInt(articleIdStr);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return new Msg(false,"更新错误");
		}
		DbUtil.beginTransaction();
		try {
			articleDao.editArticle(articleId, title, text, new Date());
			typeDao.deleteArticleTypeMapping(articleId);
			typeDao.addArticleTypeMapping(typeId, articleId);
		} catch (Exception e) {
			e.printStackTrace();
			DbUtil.rollbackTransaction();
			return new Msg(false,"更新错误");
		}
		DbUtil.commitTransaction();
		// 删除文件

		return new Msg(true,"更新文章成功");
	}

	@Override
	public List<String> editArticleImage(List<String> imgSrc, int articleId) {
		Pattern base64 = Pattern.compile("^data:image.*$");
		List<String> outputURLS = new ArrayList<String>();
		for (String src : imgSrc) {
			// 如果是base64编码的，说明是新加入的图片
			if (base64.matcher(src).matches()) {
				// 为新插入的图片分配UUID
				String uuid = UUID.randomUUID().toString().replace("-", "");
				// 将新插入的图片写入硬盘
				String path = "e:\\Java\\Project\\douban\\WebContent\\img\\Article\\" + articleId + "\\" + uuid
						+ ".jpg";
				boolean isSaveSuccess = ImageIOUtil.base64ToImage(src, path);
				// 写入硬盘不成功
				if (!isSaveSuccess) {
					return null;
				}
				// 生成新插入图片的URL
				String newURL = "/douban/getArticleImage.do?articleId=" + articleId + "&imgId=" + uuid;
				outputURLS.add(newURL);
			} else {
				outputURLS.add(src);
			}
		}
		/**
		 * TODO 新开一个线程，把不再使用的图片删除
		 */
		return outputURLS;
	}

	@Override
	public int getNextId() {
		return articleDao.getNextId();
	}

	@Override
	public BufferedImage getImage(int articleId, String imgId) {
		String path = "e:\\Java\\Project\\douban\\WebContent\\img\\Article\\" + articleId + "\\" + imgId + ".jpg";
		File file = new File(path);
		BufferedImage image = null;
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return image;

	}

	public boolean deleteUnUsedImage(int articleId, List<String> imgUUID) {
		return false;
	}


	@Override
	public Msg deleteArticle(int userId, String articleIdStr) {
		int articleId;
		try {
			articleId = Integer.parseInt(articleIdStr);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return new Msg(false,"删除失败");
		}
		DbUtil.beginTransaction();
		try {
			articleDao.deleteArticle(userId, articleId);
			DbUtil.commitTransaction();
		} catch (Exception e) {
			DbUtil.rollbackTransaction();
			e.printStackTrace();
			return new Msg(false,"删除失败");
		}
		return new Msg(true,"删除成功");
	}

}
