package project.healingcamp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import project.healingcamp.service.Community_BoardService;
import project.healingcamp.service.ReplyService;
import project.healingcamp.vo.Community_BoardVO;
import project.healingcamp.vo.PageVO;
import project.healingcamp.vo.ReplyVO;
import project.healingcamp.vo.SearchVO;
import project.healingcamp.vo.UserVo;

@RequestMapping(value="/community")
@Controller
public class CommunityController {
	
	@Autowired
	private Community_BoardService cboardService;
	
	@Autowired
	private ReplyService replyService;
	
	//Ŀ�´�Ƽ ����Ʈ �̵�
	@RequestMapping(value="/community_list.do",method=RequestMethod.GET)
	public String community_list(Model model,SearchVO searchVO) {

		//���������̼�(�˻�������)
		PageVO pageVO = new PageVO(searchVO,cboardService.total(searchVO));
		//��ü�Խñ� ������ ��û
		List<Community_BoardVO> list = cboardService.list(searchVO);
		//�����͸� �𵨿� ��� ȭ�鿡 �ѱ�
		model.addAttribute("pageVO",pageVO);//���������̼� ����
		model.addAttribute("datalist",list);//�۸�� ����
		return "community/community_list";
	}
	
	//�Խñ� �󼼺��� �̵�
	@RequestMapping(value="/community_view.do",method=RequestMethod.GET)
	public String community_view(int bidx,Model model,ReplyVO replyVO) {
		
		//ȣ��� ����� vo�� ����
		Community_BoardVO vo = cboardService.selectByBidx(bidx);
		//��ȸ�� ����
		cboardService.hitCount(bidx);
		
		//model�� vo�� ��� ȭ�鿡 �ѱ�
		model.addAttribute("vo",vo);
		
		
		return "community/community_view";
	}
	
	//��� ����Ʈ
	@RequestMapping(value="/community_view.do",method=RequestMethod.POST)
	@ResponseBody
	public List<ReplyVO> community_view(int bidx) {
		
		//��� ����Ʈ
		List<ReplyVO> reply_list = replyService.reply_list(bidx);
		
		return reply_list;
	}
	
	//�Խñ� �ۼ� ������ �̵�
	@RequestMapping(value="/community_write.do",method=RequestMethod.GET)
	public String community_write() {
		return "community/community_write";
	}
	
	@RequestMapping(value="/community_write.do",method=RequestMethod.POST)
	public String community_write(Community_BoardVO cboardVO,HttpSession session,HttpServletRequest request) {
		
		UserVo login = (UserVo)session.getAttribute("login");
		
		cboardVO.setId(login.getId());			
		cboardVO.setUidx(login.getUidx());
		cboardVO.setIp(request.getRemoteAddr());
		
		//�Խñ� �ۼ�
		cboardService.insert(cboardVO);
		//�ֱ��ۼ��� �Խñ��� bidx
		int bidx = cboardService.maxBidx();
		
		return "redirect:community_view.do?bidx="+bidx;
	}
	
	//�Խñ� ���������� �̵�
	@RequestMapping(value="/community_modify.do",method=RequestMethod.GET)
	public String community_modify(int bidx,Model model) {
		
		//�ۼ��� �Խñ��� ī�װ����� ����Ʈ�� ����
		List<Community_BoardVO> categoryList = cboardService.categoryList();
		Community_BoardVO vo = cboardService.selectByBidx(bidx);
		
		model.addAttribute("vo",vo);
		model.addAttribute("categoryList",categoryList);
		return "community/community_modify";
	}
	
	//�Խñ� ����
	@RequestMapping(value="/community_modify.do",method=RequestMethod.POST)
	public String community_modify(Community_BoardVO cboardVO) {
		
		int result = cboardService.modifyByBidx(cboardVO);
		
		return "redirect:community_view.do?bidx="+cboardVO.getBidx();
	}

	//�Խñ� ����
	@RequestMapping(value="/community_delete.do",method=RequestMethod.POST)
	public String delete(int bidx) {
		
		cboardService.deleteByBidx(bidx);
		
		return "redirect:community_list.do";
	}
	
	//�Խñ� �Ű��˾�
	@RequestMapping(value="/popup.do",method=RequestMethod.GET)
	public String popup() {
		return "community/popup";
	}
	
	//��� �ۼ�
	@RequestMapping(value="/community_reply_insert.do",method=RequestMethod.POST)
	@ResponseBody
	public String community_reply_insert(ReplyVO replyVO,HttpSession session,HttpServletRequest request,Community_BoardVO cboardVO) {
		
		//�α��� ����
		UserVo login = (UserVo)session.getAttribute("login");
		
		replyVO.setUidx(login.getUidx()); //����ۼ��� ��ȣ
		replyVO.setId(login.getId()); //����ۼ��� ���̵�
		replyVO.setBidx(cboardVO.getBidx()); //�ۼ��Ѵ���� �Խñ� ��ȣ
		replyVO.setReply_Ip(request.getRemoteAddr()); // ������
		
		//����ۼ� �� ����
		replyService.reply_Insert(replyVO);
		
		return "success";
	}
	
	//��� ����
	@RequestMapping(value="/community_reply_delete.do",method=RequestMethod.POST)
	@ResponseBody
	public String community_reply_delete(ReplyVO replyVO) {
		replyService.deleteByReply(replyVO);
		
		return "1";
	}
	
	//��� ����
	@RequestMapping(value="/community_reply_update.do",method=RequestMethod.POST)
	public String community_reply_update(ReplyVO replyVO) {
		int result = replyService.updateByReply(replyVO);
		System.out.println("result:"+result);
		System.out.println("��ȣ:"+replyVO.getReply_Idx());
		System.out.println("����:"+replyVO.getReply_Content());
		
		return "1";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}