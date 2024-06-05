package feature.screeningschedule;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import core.domain.screeningschedule.ScreeningSchedule;

class ScheduleListCellRenderer extends JPanel implements ListCellRenderer<ScreeningSchedule> {
    private JLabel label;

    public ScheduleListCellRenderer() {
        setLayout(new BorderLayout());
        label = new JLabel();
        label.setFont(new Font(null, Font.PLAIN, 20));
        label.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        add(label);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends ScreeningSchedule> list, ScreeningSchedule value, int index, boolean isSelected, boolean cellHasFocus) {
        label.setText(value.getStartDate().toString());
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            label.setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            label.setForeground(list.getForeground());
        }
        setOpaque(true);
        return this;
    }
}

public class ScheduleListView extends JPanel implements ListSelectionListener {
	private final ScheduleListViewModel viewModel;
	private JList<ScreeningSchedule> list;
	
	public ScheduleListView(ScheduleListViewModel viewModel) {
		this.viewModel = viewModel;
		
		DefaultListModel<ScreeningSchedule> listModel = new DefaultListModel<>();
		this.viewModel.scheduleList.forEach(schedule -> listModel.addElement(schedule));
		
		this.list = new JList<>(listModel);
        this.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.list.setCellRenderer(new ScheduleListCellRenderer());
        this.list.setSelectedIndex(0);
        this.setPreferredSize(new Dimension(200, 1080));

        // JScrollPane에 JList 추가
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(200, 1080));
        this.add(scrollPane);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		if (!e.getValueIsAdjusting()) {
            this.viewModel.scheduleListCellTapped(this.list.getSelectedValue());
            repaint();
        }
	}
}
