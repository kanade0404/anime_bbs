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
public class ExecuteInsert extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ExecuteInsert() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 基本的に利用しない
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}
	// サムネール画像を作成して保存するメソッド
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
		int postId = 0;
		if(postMode.equals("REPLY") == true){
			postId = Integer.parseInt(request.getParameter("POST_ID"));
		}
		String userId = request.getParameter("USER_ID");
		String postMessage = request.getParameter("MESSAGE");
		Timestamp postTime = new Timestamp(System.currentTimeMillis());
		String threadId = request.getParameter("THREAD_ID");
		// uploadフォルダーの絶対パスを調べる
		String path = getServletContext().getRealPath("upload");
		// dtoに投稿された値を挿入する
		WebBoardDto dto = new WebBoardDto();
		dto.setThreadId(threadId);
		if(postMode.equals("REPLY") == true){
			dto.setPostId(postId);
		}
		dto.setUserId(userId);
		dto.setMessages(postMessage);
		dto.setPostStatus(CommonObject.POST_STATUS_NORMAL);
		dto.setPostTime(postTime);
		int i = 0;
        for (Part part : request.getParts()) {
        	String contentType = part.getHeader("content-type");
        	if (part.getName().equals("FILENAME")) {
                String filename = getFilename(part);
                filename = new File(filename).getName();
                i++;
                if(i == 1){
                	dto.setPostImg1(filename);
                	dto.setPostImg2("");
                }else if(i == 2){
                	dto.setPostImg2(filename);
                }
                // JPEG形式の画像のみ保存する
    			if ((contentType.equals("image/jpeg")) || (contentType.equals("image/pjpeg"))) {
    				// 画像ファイルをpath＋filenameとして保存する
    				part.write(path + "\\" + filename);
    				// サムネール画像の作成
    				createThumbnail(path + "\\" + filename, path + "\\s\\" + filename, 120);
    				//createThumbnail(path + "\\" + filename, path + "\\s\\" + filename, 120);
    			}
            }
        }
        dto.setPostMode(postMode);
		WebBoardLogic logic = new WebBoardLogic();
		logic.executeInsert(out, dto);
		RequestDispatcher dispatch = request.getRequestDispatcher(CommonObject.BOARD_SERVLET);
		dispatch.forward(request, response);
	}

	private String getFilename(Part part) {
		for (String cd : part.getHeader("Content-Disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				return cd.substring(cd.indexOf('=') + 1).trim()
						.replace("\"", "");
			}
		}
		return null;
	}

}
