package webBoard;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import businessLogic.WebBoardLogic;
import common.CommonObject;
import dto.WebBoardDto;


//マルチパートデータの設定
@MultipartConfig(location="", maxFileSize=1024*1024*2)
public class ExecuteEdit extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ExecuteEdit() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 基本的に利用しない
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding(CommonObject.CHAR_CODE);
		response.setContentType(CommonObject.CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		int postId = Integer.parseInt(request.getParameter("POST_ID"));
		int replyId = Integer.parseInt(request.getParameter("REPLY_ID"));
		String postMode = request.getParameter("POST_MODE");

		//editTypeがDELETEかUPDATEどうかで処理を分けます
		if (postMode.equals("DELETE")) {
			WebBoardLogic logic = new WebBoardLogic();
			logic.executeDelete(out, postId, replyId);
			RequestDispatcher dispatch = request.getRequestDispatcher(CommonObject.BOARD_SERVLET);
			dispatch.forward(request, response);
		} else if(postMode.equals("UPDATE")) {
			processRequest(request, response);
		}
	}

	//縮小画像を作成して保存します
	private void createThumbnail(String originFile,
			String thumbnailFile, int width) {
		try {
			// 元画像の読み込み
			BufferedImage image = ImageIO.read(new File(originFile));

			// 元画像の情報を取得
			int originWidth = image.getWidth();
			int originHeight = image.getHeight();
			int type = image.getType();

			// 縮小画像の高さを計算
			int height = originHeight * width / originWidth;

			// 縮小画像の作成
			BufferedImage smallImage
			= new BufferedImage(width, height, type);
			Graphics2D g2 = smallImage.createGraphics();

			// 描画アルゴリズムの設定
			// 描画：品質優先
			g2.setRenderingHint(RenderingHints.KEY_RENDERING,
					RenderingHints.VALUE_RENDER_QUALITY);
			// アンチエイリアス：ON
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);

			// 元画像の縮小
			g2.drawImage(image, 0, 0, width, height, null);

			// 縮小画像の保存
			ImageIO.write(smallImage, "jpeg", new File(thumbnailFile));

		} catch (Exception e) {
			// 画像の縮小に失敗
			log("画像の縮小に失敗 : " + e);
		}
	}

	protected void processRequest(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding(CommonObject.CHAR_CODE);
		response.setContentType(CommonObject.CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		String postMode = request.getParameter("POST_MODE");
		int postId = Integer.parseInt(request.getParameter("POST_ID"));
		int replyId = Integer.parseInt(request.getParameter("REPLY_ID"));
		String threadId = request.getParameter("THREAD_ID");
		String userId = request.getParameter("USER_ID");
		String postMessage = request.getParameter("MESSAGE");
		Timestamp postTime = new Timestamp(System.currentTimeMillis());
		String filename = "";
		// uploadフォルダーの絶対パスを調べる
		String path = getServletContext().getRealPath("upload");
		// dtoに投稿された値を挿入する
		WebBoardDto dto = new WebBoardDto();
		dto.setThreadId(threadId);
		dto.setPostId(postId);
		dto.setUserId(userId);
		dto.setReplyId(replyId);
		dto.setMessages(postMessage);
		dto.setPostStatus(CommonObject.POST_STATUS_NORMAL);
		dto.setPostTime(postTime);
		int i = 0;
        for (Part part : request.getParts()) {
        	if (part.getName().equals("FILENAME")) {
                filename = getFilename(part);
                filename = new File(filename).getName();
                i++;
                if(i == 1){
                	dto.setPostImg1(filename);
                	dto.setPostImg2("");
                }else if(i == 2){
                	dto.setPostImg2(filename);
                }
                String contentType = part.getHeader("content-type");
                // JPEG形式の画像のみ保存する
    			if ((contentType.equals("image/jpeg")) || (contentType.equals("image/pjpeg"))) {
    				// 画像ファイルをpath＋filenameとして保存する
    				part.write(path + "\\" + filename);
    				// サムネール画像の作成
    				createThumbnail(path + "\\" + filename, path + "\\s\\" + filename, 120);
    			}
            }
        }
        dto.setPostMode(postMode);
		WebBoardLogic logic = new WebBoardLogic();
		logic.executeUpdate(out, dto);
		RequestDispatcher dispatch = request.getRequestDispatcher(CommonObject.BOARD_SERVLET);
		dispatch.forward(request, response);
	}

	private String getFilename(Part part) {
		for (String cd : part.getHeader("Content-Disposition").split(";")) {
			//fileの名前を取得します
			if (cd.trim().startsWith("filename")) {
				return cd.substring(cd.indexOf('=') + 1).trim()
						.replace("\"", "");
			}
		}
		return null;
	}
}
