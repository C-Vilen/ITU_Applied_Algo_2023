import csv

def csv_to_latex(filename, latex_filename):
    with open(filename, 'r') as csv_file:
        csv_reader = csv.reader(csv_file)
        header = next(csv_reader)
        data = list(csv_reader)

    with open(latex_filename, 'w') as latex_file:
        latex_file.write(r'\begin{tabular}{cccc}' + '\n')
        latex_file.write(' & '.join(header) + r'\\' + '\hline' + '\n')
        for row in data:
            latex_file.write(' & '.join(row) + r'\\' + '\n')
        latex_file.write(r'\end{tabular}' + '\n')

# Example usage:
csv_to_latex('resultsHyperLogLog.csv', 'output_latex_table.tex')

import csv
import matplotlib.pyplot as plt
import matplotlib.ticker as ticker
import numpy as np

def generate_bar_plot(input_csv_file, output_pdf_file):
    ele_1024 = []
    ele_2048 = []
    ele_4096 = []
    amount_of_elements = []

    with open(input_csv_file, 'r') as csv_file:
        csv_reader = csv.reader(csv_file)
        next(csv_reader)  # Skip the header row

        # Append values for each register size into related array
        for row in csv_reader:
            ele_1024.append(int(row[0]))
            ele_2048.append(int(row[1]))
            ele_4096.append(int(row[2]))
            amount_of_elements.append(int(row[3]))

    # Create bar-plot
    barWidth = 0.25
    data = [ele_1024, ele_2048, ele_4096]
    # Set position of bar on X axis
    bar1024 = np.arange(len(data[0]))
    bar2048 = [x + barWidth for x in bar1024]
    bar4096 = [x + barWidth for x in bar2048]

    # Make the plot    
    plt.bar(bar1024, data[0], width = barWidth,
            edgecolor ='grey', label ='1024', align='center')
    plt.bar(bar2048, data[1],  width = barWidth,
            edgecolor ='grey', label ='2048', align='center')
    plt.bar(bar4096, data[2],  width = barWidth,
            edgecolor ='grey', label ='4096', align='center')
    
    # Adding Xticks
    labels = ['0.1', '0.2', '0.3', '0.4', '0.5', '0.6', '0.7', '0.8', '0.9', '1.0']
    plt.xlabel('Elements (1e6)', fontsize = 10)
    plt.ylabel('Estimated cardinality (1e6)', fontsize = 10)
    plt.xticks([r + barWidth for r in range(len(data[0]))], labels)
    #plt.ticklabel_format(style='sci', axis='x', scilimits=(0,0))
    
    
    ax = plt.gca()
    ax.yaxis.set_major_locator(ticker.MultipleLocator(100000))
    ax.grid(axis = 'y')
    
    plt.title('Cardinality of HyperLogLog with different number of registers',pad=20)
    plt.legend(loc='upper left', title='Registers')
    plt.savefig(output_pdf_file, format='png')
    #plt.show()
    

# Specify the input CSV file and output PDF file
input_csv_file = 'resultsHyperLogLog.csv'
output_pdf_file = 'output_bar.png'

# Call the function to generate the histogram plot
#generate_bar_plot(input_csv_file, output_pdf_file)

def generate_deviation_bar_plot(input_csv_file, output_pdf_file):
    ele_1024 = []
    ele_2048 = []
    ele_4096 = []
    amount_of_elements = []

    with open(input_csv_file, 'r') as csv_file:
        csv_reader = csv.reader(csv_file)
        next(csv_reader)  # Skip the header row

        for row in csv_reader:
            ele_1024.append(abs(((int(row[0]) / int(row[3])) * 100) - 100))
            ele_2048.append(abs(((int(row[1]) / int(row[3])) * 100) - 100))
            ele_4096.append(abs(((int(row[2]) / int(row[3])) * 100) - 100))
            amount_of_elements.append(int(row[3]))

    # Create bar-plot
    barWidth = 0.25
    data = [ele_1024, ele_2048, ele_4096]
    x = np.arange(len(data[0]))  # X-axis values

    # Adding Xticks
    labels = ['0.1', '0.2', '0.3', '0.4', '0.5', '0.6', '0.7', '0.8', '0.9', '1.0']
    plt.xlabel('Elements (1e6)', fontsize=10)
    plt.ylabel('Deviation from elements (%)', fontsize=10)
    plt.xticks(x + barWidth * 1.5, labels)  # Adjust the x-ticks positions

    # Plot data for each register size
    plt.bar(x, ele_1024, width=barWidth, label='1024 Registers', align='center')
    plt.bar(x + barWidth, ele_2048, width=barWidth, label='2048 Registers', align='center')
    plt.bar(x + 2 * barWidth, ele_4096, width=barWidth, label='4096 Registers', align='center')

    plt.title('Deviation in HyperLogLog for different registers', pad=20)
    plt.legend(loc='upper left', title='Registers')
    
    ax = plt.gca()
    ax.grid(axis = 'y')
    
    # Adjust the figure size to accommodate the legend
    fig = plt.gcf()
    fig.set_size_inches(10, 6)
    
    
    # Make Latex Tabular
    columns = ["1024 Registers", "2048 Registers", "4096 Registers"]
    # latex_table = "\\begin{tabular}{lccc}\n"
    # latex_table += "\\textbf{Elements (1e6)} & \\textbf{" + "} & \\textbf{".join(columns) + "} \\\\\n"
    # for row, ele_1024_val, ele_2048_val, ele_4096_val in zip(labels, ele_1024, ele_2048, ele_4096):
    #     latex_table += f"{row} & {ele_1024_val:.2f} & {ele_2048_val:.2f} & {ele_4096_val:.2f} \\\\\n"
    # latex_table += "\\end{tabular}"
    
    table_data = []
    table_data.append(["Elements (1e6)"] + columns)
    for row, ele_1024_val, ele_2048_val, ele_4096_val in zip(labels, ele_1024, ele_2048, ele_4096):
        formatted_row = [row, f"{round(ele_1024_val, 2):.2f}", f"{round(ele_2048_val, 2):.2f}", f"{round(ele_4096_val, 2):.2f}"]
        table_data.append(formatted_row)
    
    # Write the table data to a CSV file
    output_csv_file = "deviation-table.csv"
    with open(output_csv_file, 'w', newline='') as csv_file:
     csv_writer = csv.writer(csv_file)
     csv_writer.writerows(table_data)

    
    
    
    plt.savefig(output_pdf_file, format='pdf')
    plt.show()  # Uncomment this line to display the plot

    

# Specify the input CSV file and output PDF file
input_csv_file = 'resultsHyperLogLog.csv'
output_png_deviation_file = 'output_bar_deviation.png'

generate_deviation_bar_plot(input_csv_file,output_png_deviation_file)
csv_to_latex('deviation-table.csv', 'deviation_latex_table.tex')




